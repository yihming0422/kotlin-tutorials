package com.bennyhuo.kotlin.coroutinebasics.prepare.go

import com.bennyhuo.kotlin.coroutinebasics.common.DispatcherContext
import com.bennyhuo.kotlin.coroutinebasics.utils.log
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

interface Channel<T> {
    suspend fun send(value: T)

    suspend fun receive(): T

    fun close()
}

class ClosedException(message: String) : Exception(message)

class SimpleChannel<T> : Channel<T> {

    sealed class Element {
        override fun toString(): String {
            return this.javaClass.simpleName
        }

        object None : Element()
        class Producer<T>(val value: T, val continuation: Continuation<Unit>) : Element()
        class Consumer<T>(val continuation: Continuation<T>) : Element()
        object Closed : Element()
    }

    private val state = AtomicReference<Element>(Element.None)

    override suspend fun send(value: T) = suspendCoroutine<Unit> { continuation ->
        val prev = state.getAndUpdate {
            when (it) {
                Element.None -> Element.Producer(value, continuation)
                is Element.Producer<*> -> throw IllegalStateException("Cannot send new element while previous is not consumed.")
                is Element.Consumer<*> -> Element.None
                Element.Closed -> throw IllegalStateException("Cannot send after closed.")
            }
        }
        (prev as? Element.Consumer<T>)?.continuation?.resume(value)?.let { continuation.resume(Unit) }
    }

    override suspend fun receive(): T = suspendCoroutine<T> { continuation ->
        val prev = state.getAndUpdate {
            when (it) {
                Element.None -> Element.Consumer(continuation)
                is Element.Producer<*> -> Element.None
                is Element.Consumer<*> -> throw IllegalStateException("Cannot receive new element while previous is not provided.")
                is Element.Closed -> throw IllegalStateException("Cannot receive new element after closed.")
            }
        }
        (prev as? Element.Producer<T>)?.let {
            it.continuation.resume(Unit)
            continuation.resume(it.value)
        }
    }

    override fun close() {
        val prev = state.getAndUpdate { Element.Closed }
        if (prev is Element.Consumer<*>) {
            prev.continuation.resumeWithException(ClosedException("Channel is closed."))
        } else if (prev is Element.Producer<*>) {
            prev.continuation.resumeWithException(ClosedException("Channel is closed."))
        }
    }
}

class QueueChannel<T> : Channel<T> {
    class Producer<T>(val value: T, val continuation: Continuation<Unit>)
    class Consumer<T>(val continuation: Continuation<T>)

    sealed class ElementList {
        object Nil : ElementList()

        abstract class AbsElementList<T> : ElementList() {
            private val list = mutableListOf<T>()

            protected abstract fun new(): AbsElementList<T>

            fun offer(element: T): ElementList {
                return new().also { it.list += this.list + element }
            }

            open fun take(): ElementList {
                val newList = this.list - this.list.first()
                if (newList.isEmpty()) return Nil
                return new().also { it.list += newList }
            }

            fun peek(): T = list.first()

            fun elements(): List<T> = Collections.unmodifiableList(list)
        }

        class ProducerList<T> : AbsElementList<Producer<T>>() {
            override fun new() = ProducerList<T>()
        }

        class ConsumerList<T> : AbsElementList<Consumer<T>>() {
            override fun new() = ConsumerList<T>()
        }

        object Closed : ElementList()
    }

    private val state = AtomicReference<ElementList>(ElementList.Nil)

    override suspend fun send(value: T) = suspendCoroutine<Unit> { continuation ->
        val prev = state.getAndUpdate {
            when (it) {
                ElementList.Nil -> ElementList.ProducerList<T>().offer(Producer(value, continuation))
                is ElementList.ProducerList<*> -> {
                    (it as ElementList.ProducerList<T>).offer(Producer(value, continuation))
                }
                is ElementList.ConsumerList<*> -> {
                    (it as ElementList.ConsumerList<T>).take()
                }
                ElementList.Closed -> throw IllegalStateException("Cannot send after closed.")
                else -> throw IllegalStateException()
            }
        }
        (prev as? ElementList.ConsumerList<T>)?.peek()?.continuation?.resume(value)?.run { continuation.resume(Unit) }
    }

    override suspend fun receive(): T = suspendCoroutine<T> { continuation ->
        val prev = state.getAndUpdate {
            when (it) {
                ElementList.Nil -> ElementList.ConsumerList<T>().offer(Consumer(continuation))
                is ElementList.ProducerList<*> -> {
                    (it as ElementList.ProducerList<T>).take()
                }
                is ElementList.ConsumerList<*> -> {
                    (it as ElementList.ConsumerList<T>).offer(Consumer(continuation))
                }
                ElementList.Closed -> throw IllegalStateException("Cannot receive after closed.")
                else -> throw IllegalStateException()
            }
        }
        (prev as? ElementList.AbsElementList<Producer<T>>)?.peek()?.let {
            it.continuation.resume(Unit)
            continuation.resume(it.value)
        }
    }

    override fun close() {
        val prev = state.getAndUpdate { ElementList.Closed }
        if (prev is ElementList.ConsumerList<*>) {
            prev.elements().forEach {
                it.continuation.resumeWithException(ClosedException("Channel is closed."))
            }
        } else if (prev is ElementList.ProducerList<*>) {
            prev.elements().forEach {
                it.continuation.resumeWithException(ClosedException("Channel is closed."))
            }
        }
    }
}

fun plainChannelSample() {
    val channel = SimpleChannel<Int>()

    go("producer"){
        for (i in 0..6) {
            log("send", i)
            channel.send(i)
        }
    }

    go("consumer", channel::close){
        for (i in 0..5) {
            log("receive")
            val got = channel.receive()
            log("got", got)
        }
    }
}

fun blockingChannelSample() {
    val channel = QueueChannel<Int>()
    for (n in 0..5) {
        go("producer $n"){
            for (i in n..n + 5) {
                log("send", i)
                channel.send(i)
            }
        }
    }

    go("consumer", channel::close) {
        for (i in 0..11) {
            val got = channel.receive()
            log("got", got)
        }
    }
}

fun go(name: String = "", completion: () -> Unit = {}, block: suspend () -> Unit){
    block.startCoroutine(object : Continuation<Any> {
        override val context = DispatcherContext()

        override fun resumeWith(result: Result<Any>) {
            log("end $name", result)
            completion()
        }
    })
}

fun main() {
    plainChannelSample()

    //blockingChannelSample()
}
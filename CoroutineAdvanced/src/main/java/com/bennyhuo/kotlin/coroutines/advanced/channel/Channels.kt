package com.bennyhuo.kotlin.coroutines.advanced.channel

import com.bennyhuo.kotlin.coroutines.advanced.utils.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun main() {
    broadcast()
}

suspend fun basics() {
//    val channel = Channel<Int>(Channel.RENDEZVOUS)
//    val channel = Channel<Int>(Channel.UNLIMITED)
//    val channel = Channel<Int>(Channel.CONFLATED)
    val channel = Channel<Int>(Channel.BUFFERED)
//    val channel = Channel<Int>(1)

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            log("sending", i)
            channel.send(i)
            log("sent", i)
        }
        channel.close()
    }

    val consumer = GlobalScope.launch {
        while (!channel.isClosedForReceive) {
            log("receiving")
            val value = channel.receiveOrNull()
            log("received", value)
        }
    }

    producer.join()
    consumer.join()
}

suspend fun iterateChannel() {
    val channel = Channel<Int>(Channel.UNLIMITED)

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            log("sending", i)
            channel.send(i)
            log("sent", i)
        }
        channel.close()
    }

    val consumer = GlobalScope.launch {
        for (i in channel) {
            log("received: ", i)
        }
    }

    producer.join()
    consumer.join()
}

suspend fun producer() {
    val receiveChannel = GlobalScope.produce(capacity = Channel.UNLIMITED) {
        for (i in 0..3) {
            log("sending", i)
            send(i)
            log("sent", i)
        }
    }

    val consumer = GlobalScope.launch {
        for (i in receiveChannel) {
            log("received: ", i)
        }
    }

    consumer.join()
}

suspend fun consumer() {
    val sendChannel = GlobalScope.actor<Int>(capacity = Channel.UNLIMITED) {
        for (i in this) {
            log("received: ", i)
        }
    }

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            log("sending", i)
            sendChannel.send(i)
            log("sent", i)
        }
    }

    producer.join()
}

suspend fun broadcast() {
    //val broadcastChannel = BroadcastChannel<Int>(Channel.BUFFERED)

    val broadcastChannel = GlobalScope.broadcast {
        for (i in 0..5) {
            send(i)
        }
    }

    List(5) { index ->
        GlobalScope.launch {
            val receiveChannel = broadcastChannel.openSubscription()
            for (i in receiveChannel) {
                log("[#$index] received: $i")
            }
        }
    }.joinAll()
}
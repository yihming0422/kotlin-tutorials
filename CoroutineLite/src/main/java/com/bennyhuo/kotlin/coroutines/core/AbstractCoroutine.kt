package com.bennyhuo.kotlin.coroutines.core

import com.bennyhuo.kotlin.coroutines.CancellationException
import com.bennyhuo.kotlin.coroutines.Job
import com.bennyhuo.kotlin.coroutines.OnCancel
import com.bennyhuo.kotlin.coroutines.OnComplete
import com.bennyhuo.kotlin.coroutines.cancel.suspendCancellableCoroutine
import com.bennyhuo.kotlin.coroutines.context.CoroutineName
import com.bennyhuo.kotlin.coroutines.scope.CoroutineScope
import com.sun.org.apache.xpath.internal.operations.Bool
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

abstract class AbstractCoroutine<T>(context: CoroutineContext):
    Job, Continuation<T>, CoroutineScope {

    protected val state = AtomicReference<CoroutineState>()

    override val context: CoroutineContext = context + this

    override val scopeContext: CoroutineContext
        get() = context

    protected val parentJob = context[Job]

    private var parentCancelDisposable: Disposable? = null

    init {
        state.set(CoroutineState.InComplete())
        parentCancelDisposable = parentJob?.invokeOnCancel {
            cancel()
        }
    }

    override val isActive: Boolean
        get() = state.get() is CoroutineState.InComplete

    override val isCompleted: Boolean
        get() = state.get() is CoroutineState.Complete<*>

    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        return doOnCompleted {
            onComplete()
        }
    }

    override fun remove(disposable: Disposable) {
        state.updateAndGet {
            prev ->
            when(prev){
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).without(disposable)
                }
                is CoroutineState.Complete<*> -> prev
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prev).without(disposable)
                }
            }
        }
    }

    override suspend fun join() {
        when(state.get()){
            is CoroutineState.Cancelling,
            is CoroutineState.InComplete -> return joinSuspend()
            is CoroutineState.Complete<*> -> {
                val currentCallingJobState = coroutineContext[Job] ?.isActive ?: return
                if(!currentCallingJobState) {
                    throw CancellationException("Coroutine is cancelled.")
                }
                return
            }
        }
    }

    private suspend fun joinSuspend() = suspendCancellableCoroutine<Unit> {
        continuation ->
        val disposable = doOnCompleted {
            result ->
            continuation.resume(Unit)
        }
        continuation.invokeOnCancel { disposable.dispose() }
    }

    protected fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        val disposable = CompletionHandlerDisposable(this, block)
        val newState = state.updateAndGet {
            prev ->
            when(prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prev).with(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }

        (newState as? CoroutineState.Complete<T>)?.let {
            block(
                when {
                    it.value != null -> Result.success(it.value)
                    it.exception != null -> Result.failure(it.exception)
                    else -> throw IllegalStateException("Won't happen!")
                }
            )
        }

        return disposable
    }

    override fun resumeWith(result: Result<T>) {
        val newState = state.updateAndGet {
            prevState ->
            when(prevState) {
                is CoroutineState.Cancelling,
                is CoroutineState.InComplete -> {
                    CoroutineState.Complete(result.getOrNull(), result.exceptionOrNull()).from(prevState)
                }
                is CoroutineState.Complete<*> -> {
                    throw IllegalStateException("Already completed!")
                }
            }
        }

        (newState as CoroutineState.Complete<T>).exception?.let(::tryHandleException)

        newState.notifyCompletion(result)
        newState.clear()
        parentCancelDisposable?.dispose()
    }

    private fun tryHandleException(e: Throwable): Boolean {
        return when(e){
            is CancellationException -> false
            else -> {
                (parentJob as? AbstractCoroutine<*>)?.handleChildException(e)?.takeIf { it }
                    ?: handleJobException(e)
            }
        }
    }

    protected open fun handleJobException(e: Throwable): Boolean {
        return false
    }

    protected open fun handleChildException(e: Throwable): Boolean {
        cancel()
        return tryHandleException(e)
    }

    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        val disposable = CancellationHandlerDisposable(this, onCancel)
        val newState = state.updateAndGet {
            prev ->
            when(prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }

        (newState as? CoroutineState.Cancelling)?.let {
            onCancel()
        }
        return disposable
    }

    override fun cancel() {
        val newState = state.updateAndGet {
            prev ->
            when(prev){
                is CoroutineState.InComplete -> {
                    CoroutineState.Cancelling().from(prev)
                }
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> {
                    prev
                }
            }
        }

        if(newState is CoroutineState.Cancelling){
            newState.notifyCancellation()
        }

        parentCancelDisposable?.dispose()
    }

    override fun toString(): String {
        return "${context[CoroutineName]?.name}"
    }
}
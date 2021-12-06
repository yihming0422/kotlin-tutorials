package com.bennyhuo.kotlin.coroutines.advanced.callback2suspend

import com.bennyhuo.kotlin.coroutines.advanced.utils.log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun main() {
    val result = CompletableFuture.supplyAsync {
        3
    }.await()

    log(result)
}

suspend fun <T> CompletableFuture<T>.await(): T {
    if(isDone){
        try {
            return get()
        } catch (e: ExecutionException) {
            throw e.cause ?: e
        }
    }
    return suspendCancellableCoroutine {
        cancellableContinuation ->
        cancellableContinuation.invokeOnCancellation {
            cancel(true)
        }

        whenComplete { value, throwable ->
            if(throwable == null){
                cancellableContinuation.resume(value)
            } else {
                cancellableContinuation.resumeWithException(throwable.cause ?: throwable)
            }
        }
    }
}


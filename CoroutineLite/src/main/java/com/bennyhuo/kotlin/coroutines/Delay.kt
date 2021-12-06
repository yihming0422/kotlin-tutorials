package com.bennyhuo.kotlin.coroutines

import com.bennyhuo.kotlin.coroutines.cancel.suspendCancellableCoroutine
import com.bennyhuo.kotlin.coroutines.utils.log
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val executor = Executors.newScheduledThreadPool(1){
    runnable ->
    Thread(runnable, "Delay-Scheduler").apply { isDaemon = true }
}

suspend fun delay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS)
    = suspendCancellableCoroutine<Unit> {
    continuation ->
    val future = executor.schedule({ continuation.resume(Unit) }, time, unit)
    continuation.invokeOnCancel {
        future.cancel(true)
    }
}
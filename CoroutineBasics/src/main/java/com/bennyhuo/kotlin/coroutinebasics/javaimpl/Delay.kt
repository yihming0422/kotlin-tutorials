package com.bennyhuo.kotlin.coroutinebasics.javaimpl

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val executor = Executors.newScheduledThreadPool(1) { runnable ->
    Thread(runnable, "Delay-Scheduler").apply { isDaemon = true }
}

@JvmOverloads
suspend fun delay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) = suspendCoroutine<Unit> { continuation ->
    val future = executor.schedule({ continuation.resume(Unit) }, time, unit)
}
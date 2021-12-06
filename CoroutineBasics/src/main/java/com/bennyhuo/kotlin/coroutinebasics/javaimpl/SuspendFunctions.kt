package com.bennyhuo.coroutines.sample2.theory

import com.bennyhuo.kotlin.coroutinebasics.javaimpl.delay
import com.bennyhuo.kotlin.coroutinebasics.utils.log
import kotlin.concurrent.thread
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume


suspend fun main() {
    log(1)
    log(returnSuspended())
    log(2)
    delay(1000)
    log(3)
    log(returnImmediately())
    log(4)
}

suspend fun returnSuspended() = suspendCoroutineUninterceptedOrReturn<String>{
    continuation ->
    thread {
        Thread.sleep(1000)
        continuation.resume("Return suspended.")
    }
    COROUTINE_SUSPENDED
}

suspend fun returnImmediately() = suspendCoroutineUninterceptedOrReturn<String>{
    "Return immediately."
}
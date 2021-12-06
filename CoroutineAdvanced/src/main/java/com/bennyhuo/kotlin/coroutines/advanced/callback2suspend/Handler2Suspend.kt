package com.bennyhuo.kotlin.coroutines.advanced.callback2suspend

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.bennyhuo.kotlin.coroutines.advanced.utils.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun <T> Handler.run(block: () -> T) = suspendCoroutine<T> { continuation ->
    post {
        try {
            continuation.resume(block())
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}

suspend fun <T> Handler.runDelay(delay: Long, block: () -> T) = suspendCancellableCoroutine<T> { continuation ->

    val message = Message.obtain(this) {
        try {
            continuation.resume(block())
        } catch (e: Exception){
            continuation.resumeWithException(e)
        }
    }.also {
        it.obj = continuation
    }

    continuation.invokeOnCancellation {
        removeCallbacksAndMessages(continuation)
    }

    sendMessageDelayed(message, delay)
}


suspend fun main() {
    Looper.prepareMainLooper()

    GlobalScope.launch {
        val handler = Handler(Looper.getMainLooper())
        val result = handler.run { "Hello" }
        val delayedResult = handler.runDelay(1000){ "World" }
        log(result, delayedResult)
        Looper.getMainLooper().quit()
    }

    Looper.loop()
}
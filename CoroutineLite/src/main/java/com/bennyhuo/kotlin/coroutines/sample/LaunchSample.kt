package com.bennyhuo.kotlin.coroutines.sample

import com.bennyhuo.kotlin.coroutines.Job
import com.bennyhuo.kotlin.coroutines.delay
import com.bennyhuo.kotlin.coroutines.dispatcher.Dispatchers
import com.bennyhuo.kotlin.coroutines.exception.CoroutineExceptionHandler
import com.bennyhuo.kotlin.coroutines.launch
import com.bennyhuo.kotlin.coroutines.scope.CoroutineScope
import com.bennyhuo.kotlin.coroutines.scope.GlobalScope
import com.bennyhuo.kotlin.coroutines.scope.coroutineScope
import com.bennyhuo.kotlin.coroutines.scope.supervisorScope
import com.bennyhuo.kotlin.coroutines.utils.log
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main(){
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        log(coroutineContext[Job], throwable)
    }

    val job = GlobalScope.launch(exceptionHandler) {
        log(1)
        delay(1000)
        supervisorScope {
            log(2)
            val job2 = launch(exceptionHandler) {
                throw ArithmeticException("Div 0")
            }
            log(3)
            job2.join()
            log(4)
        }
    }

    log(job.isActive)
    Thread.sleep(500)
    job.join()
    log("end")
}

suspend fun world(){
    coroutineScope {

    }
}

suspend fun hello() = suspendCoroutine<Int> {
    thread(isDaemon = true) {
        Thread.sleep(1000)
        it.resume(10086)
    }
}
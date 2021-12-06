package com.bennyhuo.kotlin.coroutines.sample

import com.bennyhuo.kotlin.coroutines.delay
import com.bennyhuo.kotlin.coroutines.launch
import com.bennyhuo.kotlin.coroutines.runBlocking
import com.bennyhuo.kotlin.coroutines.scope.GlobalScope
import com.bennyhuo.kotlin.coroutines.utils.log

fun main() = runBlocking {
    log(1)
    val job = GlobalScope.launch {
        log(2)
        delay(100)
        log(3)
    }
    log(4)
    job.join()
    log(5)
    delay(100)
    log(6)
}
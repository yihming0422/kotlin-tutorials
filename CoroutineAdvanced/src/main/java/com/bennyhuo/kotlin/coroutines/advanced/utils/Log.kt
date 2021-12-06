package com.bennyhuo.kotlin.coroutines.advanced.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext

val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

val now = {
    dateFormat.format(Date(System.currentTimeMillis()))
}

fun log(vararg msg: Any?) = println("${now()} [${Thread.currentThread().name}] ${msg.joinToString(" ")}")

fun stackTrace(){
    Throwable().printStackTrace(System.out)
}

fun CoroutineScope.log(vararg msg: Any?) = coroutineContext.log(*msg)

fun <T> Continuation<T>.log(vararg msg: Any?) = context.log(*msg)

fun CoroutineContext.log(vararg msg: Any?) = println("${now()} [${Thread.currentThread().name}] ${msg.joinToString(" ")}")
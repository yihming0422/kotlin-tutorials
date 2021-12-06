package com.bennyhuo.kotlin.coroutines

import com.bennyhuo.kotlin.coroutines.context.CoroutineName
import com.bennyhuo.kotlin.coroutines.core.*
import com.bennyhuo.kotlin.coroutines.dispatcher.DispatcherContext
import com.bennyhuo.kotlin.coroutines.dispatcher.Dispatchers
import com.bennyhuo.kotlin.coroutines.scope.CoroutineScope
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

private var coroutineIndex = AtomicInteger(0)

fun CoroutineScope.launch(context: CoroutineContext = EmptyCoroutineContext,
           block: suspend CoroutineScope.()-> Unit): Job {
    val completion = StandardCoroutine(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

fun <T> CoroutineScope.async(context: CoroutineContext = EmptyCoroutineContext,
          block: suspend CoroutineScope.()-> T): Deferred<T> {
    val completion = DeferredCoroutine<T>(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

fun CoroutineScope.newCoroutineContext(context: CoroutineContext): CoroutineContext {
    val combined = scopeContext + context + CoroutineName("@coroutine#${coroutineIndex.getAndIncrement()}")
    return if(combined !== Dispatchers.Default && combined[ContinuationInterceptor] == null)
        combined + Dispatchers.Default else combined
}

fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> T): T {
    val eventQueue = BlockingQueueDispatcher()
    val newContext = context + DispatcherContext(eventQueue)
    val completion = BlockingCoroutine<T>(newContext, eventQueue)
    block.startCoroutine(completion)
    return completion.joinBlocking()
}
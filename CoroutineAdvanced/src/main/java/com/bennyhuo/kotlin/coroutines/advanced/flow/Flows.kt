package com.bennyhuo.kotlin.coroutines.advanced.flow

import com.bennyhuo.kotlin.coroutines.advanced.utils.log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors

fun sequences(){
    val ints = sequence {
        yield(1)
        //delay(100)
        yield(2)
        yield(3)
    }
}

suspend fun flows(){
    val intFlow = flow {
        emit(1)
        delay(100)
        emit(2)
        emit(3)
    }

    val dispatcher = Executors.newSingleThreadExecutor { Thread(it, "MyThread").also { it.isDaemon = true } }.asCoroutineDispatcher()
    GlobalScope.launch(dispatcher) {
        intFlow.flowOn(Dispatchers.IO)
            .collect { log(it) }
    }.join()
}

suspend fun exception(){
    flow<Int> {
        emit(1)
        throw ArithmeticException("Div 0")
    }.catch {t: Throwable ->
        log("caught error: $t")
    }.onCompletion { t: Throwable? ->
        log("finally.")
    }.flowOn(Dispatchers.IO)
        .collect { log(it) }

//    flow { // bad!!!
//        try {
//            emit(1)
//            throw ArithmeticException("Div 0")
//        } catch (t: Throwable){
//            log("caught error: $t")
//        } finally {
//            log("finally.")
//        }
//    }
}

fun fromCollections(){
    listOf(1,2,3,4).asFlow()
    setOf(1,2,3,4).asFlow()
    flowOf(1,2,3,4)
}

suspend fun fromChannel(){
    val channel = Channel<Int>()
    channel.consumeAsFlow()

//    flow { // bad!!
//        emit(1)
//        withContext(Dispatchers.IO){
//            emit(2)
//        }
//    }

    channelFlow { // good
        send(1)
        withContext(Dispatchers.IO){
            send(2)
        }
    }
}

suspend fun backPressure(){
    flow {
        emit(1)
        delay(50)
        emit(2)
    }.collectLatest { value ->
        println("Collecting $value")
        delay(100) // Emulate work
        println("$value collected")
    }
}

fun rxjava(){
    Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.from(Executors.newSingleThreadExecutor { Thread(it, "MyThread-Rx") }))
        .subscribe {
            log(it)
        }
}

suspend fun main(){
    backPressure()
}
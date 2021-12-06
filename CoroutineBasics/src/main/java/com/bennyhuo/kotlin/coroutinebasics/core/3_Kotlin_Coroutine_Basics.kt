package com.bennyhuo.kotlin.coroutinebasics.core

import com.bennyhuo.kotlin.coroutinebasics.api.User
import com.bennyhuo.kotlin.coroutinebasics.api.githubApi
import com.bennyhuo.kotlin.coroutinebasics.common.DispatcherContext
import com.bennyhuo.kotlin.coroutinebasics.common.HandlerDispatcher
import com.bennyhuo.kotlin.coroutinebasics.utils.log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.*

suspend fun foo(){ }

suspend fun bar(a: Int): String {
    return "Hello"
}

//fun foo(continuation: Continuation<Unit>): Any{}
//
//fun bar(a: Int, continuation: Continuation<String>): Any{
//    return "Hello"
//}

suspend fun getUserSuspend(name: String) = suspendCoroutine<User> { continuation ->
    githubApi.getUserCallback(name).enqueue(object: Callback<User>{
        override fun onFailure(call: Call<User>, t: Throwable) =
            continuation.resumeWithException(t)
        override fun onResponse(call: Call<User>, response: Response<User>) =
            response.takeIf { it.isSuccessful }?.body()?.let(continuation::resume)
                ?: continuation.resumeWithException(HttpException(response))
    })
}


suspend fun main(){
    val user = getUserSuspend("bennyhuo")
    showUser(user)

    suspend {

    }.createCoroutine(object: Continuation<Unit>{
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            log("Coroutine End with $result")
        }
    }).resume(Unit)

    suspend {

    }.startCoroutine(object: Continuation<Unit>{
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {
            log("Coroutine End with $result")
        }
    })

    suspend {

    }.startCoroutine(object: Continuation<Unit>{
        override val context = DispatcherContext(HandlerDispatcher)

        override fun resumeWith(result: Result<Unit>) {
            log("Coroutine End with $result")
        }
    })

    suspend {

    }.startCoroutine(object: Continuation<Unit>{
        override val context = DispatcherContext(HandlerDispatcher)

        override fun resumeWith(result: Result<Unit>) {
            log("Coroutine End with $result")
        }
    })
}

suspend fun suspendFunc() = suspendCoroutine<Int> {
    it.resumeWith(Result.success(1))
}
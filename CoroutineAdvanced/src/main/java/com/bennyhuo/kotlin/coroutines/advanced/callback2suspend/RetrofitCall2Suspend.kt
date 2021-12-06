package com.bennyhuo.kotlin.coroutines.advanced.callback2suspend

import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resumeWithException

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine {
    continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }

    enqueue(object: Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.takeIf { it.isSuccessful }
        }

    })
}



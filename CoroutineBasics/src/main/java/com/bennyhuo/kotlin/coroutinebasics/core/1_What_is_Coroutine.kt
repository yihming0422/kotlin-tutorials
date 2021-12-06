package com.bennyhuo.kotlin.coroutinebasics.core

import com.bennyhuo.kotlin.coroutinebasics.api.User
import com.bennyhuo.kotlin.coroutinebasics.api.githubApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun async() {
    val call = githubApi.getUserCallback("bennyhuo")
    call.enqueue(object : Callback<User> {
        override fun onFailure(call: Call<User>, t: Throwable) {
            showError(t)
        }
        override fun onResponse(call: Call<User>, response: Response<User>) {
            response.body()?.let(::showUser) ?: showError(NullPointerException())
        }
    })
}

fun asyncLoop() {
    val names = arrayOf("abreslav","udalov", "yole")
    names.forEach { name ->
        val call = githubApi.getUserCallback(name)
        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                showError(t)
            }
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let(::showUser) ?: showError(NullPointerException())
            }
        })
    }
}

suspend fun coroutine(){
    val names = arrayOf("abreslav","udalov", "yole")
    names.forEach { name ->
        try {
            val user = githubApi.getUserSuspend(name)
            showUser(user)
        } catch (e: Exception) {
            showError(e)
        }
    }
}

suspend fun coroutineLoop(){
    val names = arrayOf("abreslav","udalov", "yole")
    val users = names.map { name ->
        githubApi.getUserSuspend(name)
    }
}
package com.bennyhuo.kotlin.coroutinebasics.core

import com.bennyhuo.kotlin.coroutinebasics.api.User

fun showUser(user: User) {
    println(user)
}

fun showError(t: Throwable) {
    t.printStackTrace(System.out)
}

fun main() {

}
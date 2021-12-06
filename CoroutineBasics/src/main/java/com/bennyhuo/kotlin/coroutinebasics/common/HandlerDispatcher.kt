package com.bennyhuo.kotlin.coroutinebasics.common

import android.os.Handler

object HandlerDispatcher: Dispatcher {
    private val handler = Handler()

    override fun dispatch(block: () -> Unit) {
        handler.post(block)
    }
}
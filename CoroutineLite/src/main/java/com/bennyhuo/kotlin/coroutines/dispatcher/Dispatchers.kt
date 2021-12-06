package com.bennyhuo.kotlin.coroutines.dispatcher

import com.bennyhuo.kotlin.coroutines.dispatcher.ui.HandlerDispatcher
import com.bennyhuo.kotlin.coroutines.dispatcher.ui.SwingDispatcher

object Dispatchers {
    val Default by lazy {
        DispatcherContext(DefaultDispatcher)
    }

    val Android by lazy {
        DispatcherContext(HandlerDispatcher)
    }

    val Swing by lazy {
        DispatcherContext(SwingDispatcher)
    }
}
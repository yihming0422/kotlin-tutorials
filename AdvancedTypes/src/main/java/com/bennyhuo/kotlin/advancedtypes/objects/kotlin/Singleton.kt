package com.bennyhuo.kotlin.advancedtypes.objects.kotlin

object Singleton: Runnable{
    override fun run() {

    }

    @JvmField var x: Int = 2
    @JvmStatic fun y(){ }

    init {

    }
}

class Foo {
    companion object {
        @JvmField var x: Int = 2
        @JvmStatic fun y(){  }
    }
}

fun main() {
    Singleton.x
    Singleton.y()

    val foo = Foo()
}
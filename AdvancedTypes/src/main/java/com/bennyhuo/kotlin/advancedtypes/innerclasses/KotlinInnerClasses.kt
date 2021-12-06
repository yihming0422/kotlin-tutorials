package com.bennyhuo.kotlin.advancedtypes.innerclasses

fun main() {
    fun localFunc(){
        println("Hello")
    }
    class LocalClass:  Cloneable, Runnable{
        override fun run() {}
    }


    // Cloneable & Runnable ; Cloneable | Runnable
    val runnableCloneable = object : Cloneable, Runnable {
        override fun run() {

        }
    }

}

// TypeScript
//fun String(array: ByteArray | CharArray){
//    when(array){
//        is ByteArray -> {
//
//        }
//        is CharArray -> {
//
//        }
//    }
//}
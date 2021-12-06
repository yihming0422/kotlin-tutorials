package com.bennyhuo.kotlin

fun main() {
    runBlock {
        List(1000) {
            println("HelloWorld")
        }
    }
}

fun runBlock(block: ()-> Unit){
    val start = System.currentTimeMillis()
    block()
    println(System.currentTimeMillis() - start)
}
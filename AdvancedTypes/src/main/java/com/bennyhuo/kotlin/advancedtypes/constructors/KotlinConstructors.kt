package com.bennyhuo.kotlin.advancedtypes.constructors

fun main() {
    val str = String()
    val str1 = String(charArrayOf('1', '2'))
}

fun String(ints: IntArray): String {
    return ints.contentToString()
}
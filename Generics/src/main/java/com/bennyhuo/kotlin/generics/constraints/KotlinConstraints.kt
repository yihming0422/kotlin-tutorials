package com.bennyhuo.kotlin.generics.constraints

import java.io.Serializable

fun <T : Comparable<T>> maxOf(a: T, b: T): T {
    return if (a > b) a else b
}

fun <T, R> callMax(a: T, b: T): R
        where T : Comparable<T>, T : () -> R,
              R : Number {
    return if (a > b) a() else b()
}

class Map<K, V> where K : Serializable, V : Comparable<V>

fun main() {
    val max = maxOf("Hello", "World")
    println(max)


}
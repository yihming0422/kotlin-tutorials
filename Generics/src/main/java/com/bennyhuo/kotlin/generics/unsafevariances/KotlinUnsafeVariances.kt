package com.bennyhuo.kotlin.generics.unsafevariances

fun main() {
    val numbers: Map<String, Number> = mapOf("a" to 1)
    val num = numbers.getOrDefault("b", 0.1)
    println(num)

    val dustbinForAny = Dustbin<Any>()
    val dustbinFotInt: Dustbin<Int> = dustbinForAny
    dustbinFotInt.put(2)
    dustbinForAny.put("Hello")

//    val int: Int = dustbinFotInt.list[1]
//    println(int)
}

class Dustbin<in T>{

    private val list = mutableListOf<@UnsafeVariance T>()

    fun put(t: T){
        list += t
    }

}

sealed class List<out T> {
    operator fun contains(t: @UnsafeVariance T): Boolean {
        fun containsInner(list: List<T>, t: T): Boolean = when (list) {
            Nil -> false
            is Cons -> if (list.head == t) true
            else containsInner(list.tail, t)
        }
        return containsInner(this, t)
    }
    object Nil : List<Nothing>() {
        override fun toString(): String {
            return "Nil"
        }

    }
    data class Cons<T>(val head: T, val tail: List<T>) : List<T>() {
        override fun toString(): String {
            return "$head, $tail"
        }

    }

    fun joinToString(sep: Char = ','): String {
        return when (this) {
            Nil -> "Nil"
            is Cons -> "${this.head}$sep${this.tail.joinToString(sep)}"
        }
    }
}

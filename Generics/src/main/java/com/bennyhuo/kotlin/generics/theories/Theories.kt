package com.bennyhuo.kotlin.generics.theories

import com.google.gson.Gson

//region -
inline fun <reified T> genericMethod(t: T){
    //val t = T()
    val ts = Array<T>(3) { TODO() }
    val jclass = T::class.java
    val list = ArrayList<T>()
    if(list is List<*>){
        println(list.joinToString())
    }
}

class Person(val age: Int, val name: String)

inline fun <reified T> Gson.fromJson(json: String): T
        = fromJson(json, T::class.java)
//endregion

open class Box<T>(val value: T)

class StringBox(value: String): Box<String>(value)

fun <T : Comparable<T>> maxOf(a: T, b: T): T {
    return if (a > b) a else b
}

fun main() {
    val max = maxOf("Hello", "World")


    //region -
//    val person = Person(18, "Bennyhuo")
//
//    val gson = Gson()
//
//    println(gson.toJson(person))
//
//    val person2: Person = gson.fromJson("""{"age":18,"name":"Bennyhuo"}""")
//    val person3 = gson.fromJson<Person>("""{"age":18,"name":"Bennyhuo"}""")
    //endregion
}
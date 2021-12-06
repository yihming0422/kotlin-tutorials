package com.bennyhuo.kotlin.reflections.basics

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.typeOf

@ExperimentalStdlibApi
fun main() {
    var cls: KClass<String> = String::class



    val property = cls.declaredMemberProperties.firstOrNull()

    val mapCls = Map::class
    println(mapCls)
    val mapType = typeOf<Map<String, Int>>()

    mapType.arguments.forEach {
        println(it)
    }
}

object B {
    fun hello(){

    }
}

class A {
    fun String.hello(){

    }
}

open class Super<T>

class Sub: Super<String>()
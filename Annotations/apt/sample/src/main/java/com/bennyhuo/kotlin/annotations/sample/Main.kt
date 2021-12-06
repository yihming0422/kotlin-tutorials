package com.bennyhuo.kotlin.annotations.sample

import com.bennyhuo.kotlin.annotations.apt.ModelMap

fun main() {
    val sample = Sample(0, "1")
    val sample2 = sample.toMap().toSample2()
    println(sample)
    println(sample2)
}

@ModelMap
data class Sample(val a: Int, val b: String)

//fun Sample.toMap() = mapOf("a" to a, "b" to b)
//fun <V> Map<String, V>.toSample() = Sample(this["a"] as Int, this["b"] as String)

@ModelMap
data class Sample2(val a: Int, val b: String)

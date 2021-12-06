package com.bennyhuo.kotlin.expressions.vars

const val b = 3

class KotlinVars {
    companion object {
        const val b = 3
    }
}

object KotlinVars2 {
    const val b = 3
}


class X {
    val b: Int
        get() {
            return (Math.random() * 100).toInt()
        }
}

fun main() {
    var a = 2
    a = 3


    val b = 3


    val c: Int

    if (a == 3) {
        c = 4
    } else {
        c = 5
    }


    val person = Person(18, "Bennyhuo")
    person.age = 19
}

class Person(var age: Int, var name: String)
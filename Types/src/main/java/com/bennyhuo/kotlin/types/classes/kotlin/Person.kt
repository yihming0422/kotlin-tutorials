package com.bennyhuo.kotlin.types.classes.kotlin

class Person(age: Int, name: String) {
    var age: Int = age //property
        get() {
            return field
        }
        set(value) {
            println("setAge: $value")
            field = value
        }
    var name: String = name
        get() {
            return field // backing field
        }
        set(value) {

        }
}

fun main() {
    val ageRef = Person::age
    val person = Person(18, "Bennyhuo")
    val nameRef = person::name
    ageRef.set(person, 20)
    nameRef.set("Andyhuo")
}
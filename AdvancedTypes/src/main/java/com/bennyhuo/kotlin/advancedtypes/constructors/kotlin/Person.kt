package com.bennyhuo.kotlin.advancedtypes.constructors.kotlin


abstract class Animal
class Person(var age: Int, var name: String = "unknown") {
    override fun equals(other: Any?) = (other as? Person)?.name?.equals(name) ?: false
    override fun hashCode() = name.hashCode()
}

val persons = HashMap<String, Person>()
fun Person(name: String): Person {
    return persons[name]
        ?: Person(1, name).also { persons[name] = it }
}
package com.bennyhuo.kotlin.advancedtypes.eg.json.gson

import com.bennyhuo.kotlin.advancedtypes.eg.json.ks.Person
import com.google.gson.Gson

// Gson

data class Person(val name: String, val age: Int)

fun main(){
    val gson = Gson()
    println(gson.toJson(Person("Benny Huo", 20)))
    println(gson.fromJson("""{"name":"Benny Huo","age":20}""", Person::class.java))
}

// output:
// {"name":"Benny Huo","age":20}
// Person(name=Benny Huo, age=20)
package com.bennyhuo.kotlin.advancedtypes.eg.json.ks

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

//Kotlinx.serialization

@Serializable
data class Person(val name: String, val age: Int)


@ImplicitReflectionSerializer
fun main(){
    println(Json.stringify(Person("Benny Huo", 20)))
    println(Json.parse<Person>("""{"name":"Benny Huo","age":20}"""))
}

//output:
//{"name":"Benny Huo","age":20}
//Person(name=Benny Huo, age=20)
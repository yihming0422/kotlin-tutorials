package com.bennyhuo.kotlin.advancedtypes.eg.json.ks

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

// KS

@Serializable
data class PersonWithDefaults(val name: String, val age: Int = 18)


@ImplicitReflectionSerializer
fun main(){
    println(Json.stringify(PersonWithDefaults("Benny Huo")))
    println(Json.parse<PersonWithDefaults>("""{"name":"Benny Huo"}"""))
}

//output:
//{"name":"Benny Huo","age":18}
//PersonWithDefaults(name=Benny Huo, age=18)
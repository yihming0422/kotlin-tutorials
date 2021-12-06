package com.bennyhuo.kotlin.advancedtypes.eg.json.ks

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

// KS

@Serializable
data class PersonWithInits(val name: String, val age: Int){

    val firstName by lazy {
        name.split(" ")[0]
    }

    //@Transient
    val lastName = name.split(" ")[1]

}


@ImplicitReflectionSerializer
fun main(){
    println(Json.stringify(PersonWithInits("Benny Huo", 18)))
    val personWithInits = Json.parse<PersonWithInits>("""{"name":"Benny Huo","age":20}""")
//    val personWithInits = Json.parse<PersonWithInits>("""{"name":"Benny Huo","age":20, "lastName":"Secret"}""")
    println(personWithInits.firstName)
    println(personWithInits.lastName)
}

//output:
//{"name":"Benny Huo","age":18,"lastName":"Huo"}
//Benny
//Huo
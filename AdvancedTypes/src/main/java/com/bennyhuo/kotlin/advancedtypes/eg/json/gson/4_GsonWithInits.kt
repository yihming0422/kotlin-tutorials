package com.bennyhuo.kotlin.advancedtypes.eg.json.gson

import com.bennyhuo.kotlin.advancedtypes.dataclasses.PoKo
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder

//Gson

@PoKo // for noArg
data class PersonWithInits(val name: String, val age: Int){

    val firstName by lazy {
        name.split(" ")[0]
    }

    //val lastName = name.split(" ")[1] // name is set later.

    val lastName by lazy {
        name.split(" ")[1]
    }

}

// Exclude Lazy values.
object LazyExclusionStrategy: ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>) = false

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.declaredType == Lazy::class.java
    }
}

fun main(){
    val gson = GsonBuilder()
        .addSerializationExclusionStrategy(LazyExclusionStrategy)
        .addDeserializationExclusionStrategy(LazyExclusionStrategy)
        .create()
    println(gson.toJson(PersonWithInits("Benny Huo", 18)))
    val personWithInits = gson.fromJson("""{"name":"Benny Huo","age":20}""", PersonWithInits::class.java)
    println(personWithInits.firstName)
    println(personWithInits.lastName)
}

// Gradle config: invokeInitializers = false
//output:
//{"name":"Benny Huo","age":18}
//Exception in thread "main" java.lang.NullPointerException
//	at com.bennyhuo.kotlin.advancedtypes.eg.json.gson.PersonWithInits.getFirstName(4_GsonWithInits.kt)
//	at com.bennyhuo.kotlin.advancedtypes.eg.json.gson._4_GsonWithInitsKt.main(4_GsonWithInits.kt:39)
//	at com.bennyhuo.kotlin.advancedtypes.eg.json.gson._4_GsonWithInitsKt.main(4_GsonWithInits.kt)

// Gradle config: invokeInitializers = true
//output:
//{"name":"Benny Huo","age":18}
//Benny
//Huo

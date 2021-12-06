package com.bennyhuo.kotlin.advancedtypes.eg.json.moshi

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

//Moshi

@JsonClass(generateAdapter = true)
data class PersonWithDefaults(val name: String, val age: Int = 18)

fun main(){
    val moshi = Moshi.Builder()
        //.add(KotlinJsonAdapterFactory()) // implementation("com.squareup.moshi:moshi-kotlin:1.8.0")
        .build()
    val jsonAdapter = moshi.adapter(PersonWithDefaults::class.java)

    println(jsonAdapter.toJson(PersonWithDefaults("Benny Huo")))
    println(jsonAdapter.fromJson("""{"name":"Benny Huo"}"""))
}

//output:
//{"name":"Benny Huo","age":18}
//PersonWithDefaults(name=Benny Huo, age=18)
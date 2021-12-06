package com.bennyhuo.kotlin.advancedtypes.eg.json.moshi

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

//Moshi

@JsonClass(generateAdapter = true)
data class PersonWithInits(val name: String, val age: Int){

    val firstName by lazy {
        name.split(" ")[0]
    }

    //@Transient
    val lastName = name.split(" ")[1]

}


fun main(){
    val moshi = Moshi.Builder()
//        .add(KotlinJsonAdapterFactory()) // implementation("com.squareup.moshi:moshi-kotlin:1.8.0")
        .build()
    val jsonAdapter = moshi.adapter(PersonWithInits::class.java)

    println(jsonAdapter.toJson(PersonWithInits("Benny Huo", 18)))
//    val personWithInits = jsonAdapter.fromJson("""{"name":"Benny Huo","age":20}""")
    val personWithInits = jsonAdapter.fromJson("""{"name":"Benny Huo","age":20, "lastName":"Secret"}""")
    println(personWithInits?.firstName)
    println(personWithInits?.lastName)
}

//output:
//{"name":"Benny Huo","age":18}
//Benny
//Huo
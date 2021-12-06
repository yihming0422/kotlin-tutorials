package com.bennyhuo.kotlin.advancedtypes.eg.json.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class PersonWithDate(val name: String, val age: Int, val birthDate: Date)

object DateSerializer {
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    @ToJson fun serialize(date: Date) = df.format(date)

    @FromJson fun deserialize(date: String) = df.parse(date)
}

fun main(){
    val moshi = Moshi.Builder().add(DateSerializer).build()
    val jsonAdapter = moshi.adapter(PersonWithDate::class.java)

    println(jsonAdapter.toJson(
        PersonWithDate(
            "Benny Huo",
            20,
            Date()
        )
    ))
    println(jsonAdapter.fromJson("""{"name":"Benny Huo","age":20,"birthDate":"2019-10-13 15:58"}"""))
}

//output:
//{"age":20,"birthDate":"2019-10-27 09:32","name":"Benny Huo"}
//PersonWithDate(name=Benny Huo, age=20, birthDate=Sun Oct 13 15:58:00 CST 2019)
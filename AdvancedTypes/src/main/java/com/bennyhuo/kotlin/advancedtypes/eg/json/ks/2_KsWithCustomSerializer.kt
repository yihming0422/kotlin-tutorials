package com.bennyhuo.kotlin.advancedtypes.eg.json.ks

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.json.Json
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Serializable
data class PersonWithDate(val name: String, val age: Int,
                          @Serializable(with= DateSerializer::class)
                          val birthDate: Date
)

@Serializer(forClass = Date::class)
object DateSerializer: KSerializer<Date> {
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    override val descriptor: SerialDescriptor = StringDescriptor.withName("Date")

    override fun serialize(encoder: Encoder, obj: Date) {
        encoder.encodeString(df.format(obj))
    }

    override fun deserialize(decoder: Decoder): Date {
        return df.parse(decoder.decodeString())
    }
}

@ImplicitReflectionSerializer
fun main(){
    println(Json.stringify(PersonWithDate("Benny Huo", 20, Date())))
    println(Json.parse<PersonWithDate>("""{"name":"Benny Huo","age":20,"birthDate":"2019-10-13 15:58"}"""))
}

//output:
//{"name":"Benny Huo","age":20,"birthDate":"2019-10-27 09:38"}
//PersonWithDate(name=Benny Huo, age=20, birthDate=Sun Oct 13 15:58:00 CST 2019)
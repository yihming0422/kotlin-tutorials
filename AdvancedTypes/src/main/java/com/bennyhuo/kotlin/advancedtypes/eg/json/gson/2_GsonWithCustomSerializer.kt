package com.bennyhuo.kotlin.advancedtypes.eg.json.gson

import com.google.gson.*
import java.lang.reflect.Type
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class PersonWithDate(val name: String, val age: Int, val birthDate: Date)

object Serializers {
    private val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")

    object DateDeserializer : JsonDeserializer<Date> {
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
            return df.parse(json.asString)
        }
    }

    object DateSerializer : JsonSerializer<Date> {
        override fun serialize(src: Date, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(df.format(src))
        }
    }
}

fun main() {
    val gson = GsonBuilder()
        .registerTypeAdapter(
            Date::class.java,
            Serializers.DateSerializer
        )
        .registerTypeAdapter(
            Date::class.java,
            Serializers.DateDeserializer
        )
        .create()
    println(gson.toJson(PersonWithDate("Benny Huo", 20, Date())))
    println(gson.fromJson("""{"name":"Benny Huo","age":20,"birthDate":"2019-10-13 15:58"}""", PersonWithDate::class.java))
}
// output:
//{"name":"Benny Huo","age":20,"birthDate":"2019-10-27 09:27"}
//PersonWithDate(name=Benny Huo, age=20, birthDate=Sun Oct 13 15:58:00 CST 2019)
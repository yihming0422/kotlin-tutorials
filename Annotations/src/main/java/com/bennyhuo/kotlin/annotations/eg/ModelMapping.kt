package com.bennyhuo.kotlin.annotations.eg

import java.lang.IllegalArgumentException
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.text.StringBuilder

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class FieldName(val name: String)

@Target(AnnotationTarget.CLASS)
annotation class MappingStrategy(val klass: KClass<out NameStrategy>)

interface NameStrategy {
    fun mapTo(name: String): String
}

object UnderScoreToCamel : NameStrategy {
    // html_url -> htmlUrl
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) { acc, c ->
            when (acc.lastOrNull()) {
                '_' -> acc[acc.lastIndex] = c.toUpperCase()
                else -> acc.append(c)
            }
            acc
        }.toString()
    }
}

object CamelToUnderScore : NameStrategy {
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) { acc, c ->
            when {
                c.isUpperCase() -> acc.append('_').append(c.toLowerCase())
                else -> acc.append(c)
            }
            acc
        }.toString()
    }
}

@MappingStrategy(CamelToUnderScore::class)
data class UserVO(
    val login: String,
    //@FieldName("avatar_url")
    val avatarUrl: String,
    var htmlUrl: String
)

data class UserDTO(
    var id: Int,
    var login: String,
    var avatar_url: String,
    var url: String,
    var html_url: String
)

fun main() {
    val userDTO = UserDTO(
        0,
        "Bennyhuo",
        "https://avatars2.githubusercontent.com/u/30511713?v=4",
        "https://api.github.com/users/bennyhuo",
        "https://github.com/bennyhuo"
    )

    val userVO: UserVO = userDTO.mapAs()
    println(userVO)

    val userMap = mapOf(
        "id" to 0,
        "login" to "Bennyhuo",
        "avatar_url" to "https://api.github.com/users/bennyhuo",
        "html_url" to "https://github.com/bennyhuo",
        "url" to "https://api.github.com/users/bennyhuo"
    )

    val userVOFromMap: UserVO = userMap.mapAs()
    println(userVOFromMap)
}

inline fun <reified From : Any, reified To : Any> From.mapAs(): To {
    return From::class.memberProperties.map { it.name to it.get(this) }
        .toMap().mapAs()
}

inline fun <reified To : Any> Map<String, Any?>.mapAs(): To {
    return To::class.primaryConstructor!!.let {
        it.parameters.map { parameter ->
            parameter to (this[parameter.name]
                ?: (parameter.annotations.filterIsInstance<FieldName>().firstOrNull()?.name?.let(this::get))
                ?: To::class.findAnnotation<MappingStrategy>()?.klass?.objectInstance?.mapTo(parameter.name!!)?.let(this::get)
                ?: if (parameter.type.isMarkedNullable) null
                else throw IllegalArgumentException("${parameter.name} is required but missing."))
        }.toMap()
            .let(it::callBy)
    }
}
@file:JvmName("Hello")

package com.bennyhuo.kotlin.annotations.basics

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Api(val url: String)

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Path(val name: String = "")

@Target(AnnotationTarget.FUNCTION)
annotation class Get(val name: String)

@Api("https://api.github.com")
interface GitHubApi {

    @Get("/users/{name}")
    fun getUser(@Path name: String): User
}

class User
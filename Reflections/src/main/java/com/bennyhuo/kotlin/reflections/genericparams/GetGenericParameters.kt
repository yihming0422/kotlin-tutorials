package com.bennyhuo.kotlin.reflections.genericparams

import com.bennyhuo.kotlin.reflections.prepare.UserDTO
import java.lang.reflect.ParameterizedType
import kotlin.reflect.full.allSupertypes
import kotlin.reflect.full.declaredFunctions

interface Api {
    fun getUsers(): List<UserDTO>
}

abstract class SuperType<T> {
    val typeParameter by lazy {
        this::class.supertypes.first().arguments.first().type!!
    }

    val typeParameterJava by lazy {
        this.javaClass.genericSuperclass.safeAs<ParameterizedType>()!!.actualTypeArguments.first()
    }
}

class SubType : SuperType<String>()

fun main() {
    Api::class.declaredFunctions.first { it.name == "getUsers" }
        .returnType.arguments.forEach {
        println(it)
    }

    Api::getUsers.returnType.arguments.forEach {
        println(it)
    }

    Api::class.java.getDeclaredMethod("getUsers")
        .genericReturnType.safeAs<ParameterizedType>()?.actualTypeArguments?.forEach {
        println(it)
    }

    val subType = SubType()
    subType.typeParameter.let(::println)
    subType.typeParameterJava.let(::println)
}

fun <T> Any.safeAs(): T? {
    return this as? T
}
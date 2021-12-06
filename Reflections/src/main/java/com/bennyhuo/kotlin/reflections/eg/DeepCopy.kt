package com.bennyhuo.kotlin.reflections.eg

import com.bennyhuo.kotlin.reflections.prepare.deepCopy
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

fun <T : Any> T.deepCopy(): T {
    if(!this::class.isData){
        return this
    }

    return this::class.primaryConstructor!!.let {
        primaryConstructor ->
        primaryConstructor.parameters.map { parameter ->
            val value = (this::class as KClass<T>).memberProperties.first { it.name == parameter.name }
                .get(this)
            if((parameter.type.classifier as? KClass<*>)?.isData == true){
                parameter to value?.deepCopy()
            } else {
                parameter to value
            }
        }.toMap()
            .let(primaryConstructor::callBy)
    }
}

//region demo
data class Person(val id: Int, val name: String, val group: Group)

data class Group(val id: Int, val name: String, val location: String)

fun main() {
    val person = Person(
        0,
        "Bennyhuo",
        Group(
            0,
            "Kotliner.cn",
            "China"
        )
    )

    val copiedPerson = person.copy()
    val deepCopiedPerson = person.deepCopy()

    println(person === copiedPerson) //false
    println(person === deepCopiedPerson) //false

    println(person.group === copiedPerson.group) //true for shallow copy.
    println(person.group === deepCopiedPerson.group) //false

    println(deepCopiedPerson)
}
//endregion
package com.bennyhuo.kotlin.expressions.operators

fun main() {
    //https://kotlinlang.org/docs/reference/operator-overloading.html

    "Hello" == "World"
    "Hello".equals("World")

    2 + 3
    2.plus(3)

    val list = listOf(1, 2, 3, 4)

    2 in list

    list.contains(2)

    val map = mutableMapOf(
        "Hello" to 2,
        "World" to 3
    )
    val value = map["Hello"]

//    val value = map.get("Hello")

    map["World"] = 4
    map.set("World", 4)


    val func = fun(){
        println("Hello")
    }

    2 > 3
    2.compareTo(3) > 0

    func.invoke()

    func()

    2 to 3

    2.to(3)





    println("HelloWorld" rotate 5)

    val book = Book()
    val desk = Desk()
    book on desk

}

infix fun String.rotate(count: Int): String {
    val index = count % length
    return this.substring(index) + this.substring(0, index)
}

class Book
class Desk

infix fun Book.on(desk: Desk){

}

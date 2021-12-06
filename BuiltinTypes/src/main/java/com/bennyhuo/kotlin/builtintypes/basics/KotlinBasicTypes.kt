package com.bennyhuo.kotlin.builtintypes.basics

fun main() {

    var a = 2
    val b = "Hello Kotlin"

//    val c = 12345678910l // compile error.
    val c = 12345678910L // ok

    val d = 3.0 // Double, 3.0f Float

    val e: Int = 10
    //val f: Long = e // implicitness not allowed
    val f: Long = e.toLong() // implicitness not allowed

    val float1: Float = 1f
    val double1 = 1.0

    val g: UInt = 10u
    val h: ULong = 100000000000000000u

    val i: UByte = 1u

    println("Range of Int: [${Int.MIN_VALUE}, ${Int.MAX_VALUE}]")
    println("Range of UInt: [${UInt.MIN_VALUE}, ${UInt.MAX_VALUE}]")

    val j = "I❤️China"
    println("Value of String 'j' is: $j") // no need brackets
    println("Length of String 'j' is: ${j.length}") // need brackets
    System.out.printf("Length of String 'j' is: %d\n", j.length)

    val k = "Today is a sunny day."
    val m = String("Today is a sunny day.".toCharArray())
    println(k === m) // compare references.
    println(k == m) // compare values.

    val n = """
                <!doctype html>
                <html>
                <head>
                    <meta charset="UTF-8"/>
                    <title>Hello World</title>
                </head>
                <body>
                    <div id="container">
                        <H1>Hello World</H1>
                        <p>This is a demo page.</p>
                    </div>
                </body>
                </html>
                """.trimIndent()

    println(n)
}
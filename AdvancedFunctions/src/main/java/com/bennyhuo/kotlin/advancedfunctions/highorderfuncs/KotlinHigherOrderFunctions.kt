package com.bennyhuo.kotlin.advancedfunctions.highorderfuncs

fun main() {
    cost {
        val fibonacciNext = fibonacci()
        for (i in 0..10) {
            println(fibonacciNext())
        }
    }

    // region +折叠
    val intArray = IntArray(5){ it + 1 }
    intArray.forEach {
        println(it)
    }

    intArray.forEach(::println)

    intArray.forEach {
        println("Hello $it")
    }
    //endregion
}

//region +折叠
fun needsFunction(block: () -> Unit) {
    block()
}

fun returnsFunction(): () -> Long {
    return { System.currentTimeMillis() }
}
//endregion

fun cost(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println("${System.currentTimeMillis() - start}ms")
}

fun fibonacci(): () -> Long {
    var first = 0L
    var second = 1L
    return {
        val next = first + second
        val current = first
        first = second
        second = next
        current
    }
}
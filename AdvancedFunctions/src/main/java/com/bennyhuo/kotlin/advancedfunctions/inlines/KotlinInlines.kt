package com.bennyhuo.kotlin.advancedfunctions.inlines

fun main() {
    //region local return
    val ints = intArrayOf(1, 2, 3, 4)
    ints.forEach {
        if(it == 3) return@forEach
        println("Hello $it")
    }

    for (element in ints) {
        if(element == 3) continue
        println("Hello $element")
    }
    //endregion

    //region non-local return
    nonLocalReturn {
        //return
    }

    Runnable {
        println("xxx")
        println("yyy")
    }
    //endregion

    //region cost
    cost {
        println("Hello")
    }
    //endregion

    //region inline property
    money = 5.0
    //endregion
}

inline fun Runnable(noinline block: () -> Unit): Runnable {
    return object : Runnable {
        override fun run() {
            block()
        }
    }
}

public inline fun IntArray.forEach(crossinline action: (Int) -> Unit): Unit {
    for (element in this) action(element)
}

inline fun nonLocalReturn(block: () -> Unit){
    block()
}

inline fun hello(){
    println("Hello")
}

 inline fun cost(block: () -> Unit) {
    val start = System.currentTimeMillis()
    block()
    println(System.currentTimeMillis() - start)
}

var pocket: Double = 0.0
var money: Double
    inline get() = pocket
    inline set(value) {
         pocket = value
    }
package com.bennyhuo.kotlin.types.extensions

class PoorGuy{
    var pocket: Double = 0.0
}

fun PoorGuy.noMoney(){

}

//property = backing field + getter + setter
var PoorGuy.moneyLeft: Double
    get() {
        return this.pocket
    }
    set(value) {
        pocket = value
    }

interface Guy {
    var moneyLeft: Double
        get() {
            return 0.0
        }
        set(value) {

        }

    fun noMoney(){
        println("no money called.")
    }
}














fun String.padding(count: Int, char: Char = ' '): String {
    val padding = (1 .. count).joinToString(""){ char.toString() }
    return "${padding}${this}${padding}"
}

fun String.isEmail(): Boolean {
    return matches(Regex("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
}

fun String.times(count: Int): String {
    return (1 .. count).joinToString("") { this }
}

fun main() {

    "admin@bennyhuo.com".isEmail()


    println("Hello".padding(1))
    println("*".times(10))

    val stringTimes = String::times
    val stringTimesBound = "*"::times

    arrayOf(1,3,3).forEach {  }

    val x = 2
}
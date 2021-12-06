package com.bennyhuo.kotlin.advancedtypes.delegates

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Person(val name: String){
    val firstName by lazy {
        name.split(" ")[0]
    }
    val lastName by lazy {
        name.split(" ")[1]
    }
}

class StateManager {
    var state: Int by Delegates.observable(0) {
        property, oldValue, newValue ->
        println("State changed from $oldValue -> $newValue")
    }
}


class Foo {
    val x: Int by X()

    var y: Int by X()
}

class X {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return 2
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, i: Int) {

    }
}

fun main() {
    val stateManager = StateManager()
    stateManager.state = 3
    stateManager.state = 4

    println(Foo().x)
}
package com.bennyhuo.kotlin.builtintypes.functions

fun main(vararg args: String) {
    println(args.contentToString())

    val x:(Foo, String, Long)->Any = Foo::bar
    val x0: Function3<Foo, String, Long, Any> = Foo::bar
    // (Foo, String, Long)->Any = Foo.(String, Long)->Any = Function3<Foo, String, Long, Any>

    val y: (Foo, String, Long) -> Any = x
    val z: Function3<Foo, String, Long, Any> = x

    yy(x)

    val f: ()->Unit = ::foo
    val g: (Int) ->String = ::foo
    val h: (Foo, String, Long)->Any
            = Foo::bar

    multiParameters(1, 2, 3, 4)

    defaultParameter(y = "Hello")

    val (a, b, c) = multiReturnValues() //伪
    val r = a + b
    val r1 = a + c

}

fun yy(p: (Foo, String, Long) -> Any){
    //p(Foo(), "Hello", 3L)
}

class Foo {
    fun bar(p0: String, p1: Long): Any{ TODO() }
}

fun foo() { }
fun foo(p0: Int): String { TODO() }

fun defaultParameter(x: Int = 5, y: String, z: Long = 0L){
    TODO()
}

fun multiParameters(vararg ints: Int){
    println(ints.contentToString())
}

fun multiReturnValues(): Triple<Int, Long, Double> {
    return Triple(1, 3L, 4.0)
}


package com.bennyhuo.kotlin.generics.startprojections



fun main() {
    //region fold
    val queryMap: QueryMap<*, *> = QueryMap<String, Int>()
    queryMap.getKey()
    queryMap.getValue()

    val f: Function<*, *> = Function<Number, Any>()
    //f.invoke()

    if (f is Function) {
        (f as Function<Number, Any>).invoke(1, Any())
    }

    maxOf(1, 3)

    HashMap<String, List<*>>()
    //endregion

    val hashMap: HashMap<*, *> = HashMap<String, Int>()
    //hashMap.get()

}


//region fold
class QueryMap<out K : CharSequence, out V : Any> {
    fun getKey(): K = TODO()
    fun getValue(): V = TODO()
}

fun <T : Comparable<T>> maxOf(a: T, b: T): T {
    return if (a > b) a else b
}

class Function<in P1, in P2> {
    fun invoke(p1: P1, p2: P2) = Unit
}
//endregion
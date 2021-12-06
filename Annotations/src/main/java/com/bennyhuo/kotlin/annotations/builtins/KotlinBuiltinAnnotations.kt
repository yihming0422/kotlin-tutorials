@file:JvmName("KotlinAnnotations")
@file:JvmMultifileClass
package com.bennyhuo.kotlin.annotations.builtins

import java.io.IOException

@Volatile
var volatileProperty: Int = 0

@Synchronized
fun synchronizedFunction(){

}

val lock = Any()
fun synchronizedBlock(){
    synchronized(lock) {

    }
}

@Throws(IOException::class)
fun throwException(){

}

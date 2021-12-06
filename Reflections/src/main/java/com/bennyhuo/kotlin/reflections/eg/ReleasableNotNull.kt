package com.bennyhuo.kotlin.reflections.eg

import java.lang.IllegalStateException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

//region impl
fun <T : Any> releasableNotNull() = ReleasableNotNull<T>()

class ReleasableNotNull<T: Any>: ReadWriteProperty<Any, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Not initialized or released already.")
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }

    fun isInitialized() = value != null

    fun release() {
        value = null
    }
}

inline val KProperty0<*>.isInitialized: Boolean
    get() {
        isAccessible = true
        return (this.getDelegate() as? ReleasableNotNull<*>)?.isInitialized()
            ?: throw IllegalAccessException("Delegate is not an instance of ReleasableNotNull or is null.")
    }

fun KProperty0<*>.release() {
    isAccessible = true
    (this.getDelegate() as? ReleasableNotNull<*>)?.release()
        ?: throw IllegalAccessException("Delegate is not an instance of ReleasableNotNull or is null.")
}

//endregion

//region demo
class Bitmap(val width: Int, val height: Int)

class Activity {
    private var bitmap by releasableNotNull<Bitmap>()

    fun onCreate(){
        println(this::bitmap.isInitialized)
        bitmap = Bitmap(1920, 1080)
        println(::bitmap.isInitialized)
    }

    fun onDestroy(){
        println(::bitmap.isInitialized)
        ::bitmap.release()
        println(::bitmap.isInitialized)
    }
}

fun main() {
    val activity = Activity()
    activity.onCreate()
    activity.onDestroy()
}
//endregion
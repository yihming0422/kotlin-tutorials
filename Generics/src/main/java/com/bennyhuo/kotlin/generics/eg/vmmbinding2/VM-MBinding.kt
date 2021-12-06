package com.bennyhuo.kotlin.generics.eg.vmmbinding2

import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KProperty

abstract class AbsModel {
    init {
        Models.run { this@AbsModel.register() }
    }
}

class DatabaseModel : AbsModel() {
    fun query(sql: String): Int = 0
}

class NetworkModel : AbsModel() {
    fun get(url: String): String = """{"code": 0}"""
}

class SpModel : AbsModel() {
    init {
        Models.run { register("SpModel2") }
    }

    fun hello() = println("HelloWorld")
}

object Models {
    private val modelMap = ConcurrentHashMap<String, AbsModel>()

    fun AbsModel.register(name: String = this.javaClass.simpleName) {
        modelMap[name] = this
    }

    fun <T: AbsModel> String.get(): T {
        return modelMap[this] as T
    }
}

fun initModels() {
    DatabaseModel()
    NetworkModel()
    SpModel()
}

object ModelDelegate {
    operator fun <T: AbsModel> getValue(thisRef: Any, property: KProperty<*>): T {
        return Models.run {
            property.name.capitalize().get()
        }
    }
}

class MainViewModel {
    val databaseModel: DatabaseModel by ModelDelegate
    val networkModel: NetworkModel by ModelDelegate
    val spModel: SpModel by ModelDelegate
    val spModel2: SpModel by ModelDelegate
    // val compileKotlin: KotlinCompile by tasks
    // val compileTestKotlin: KotlinCompile by tasks
}

fun main() {
    initModels()
    val mainViewModel = MainViewModel()
    mainViewModel.databaseModel.query("select * from mysql.user").let(::println)
    mainViewModel.networkModel.get("https://www.imooc.com").let(::println)
    mainViewModel.spModel.hello()
    mainViewModel.spModel2.hello()
}
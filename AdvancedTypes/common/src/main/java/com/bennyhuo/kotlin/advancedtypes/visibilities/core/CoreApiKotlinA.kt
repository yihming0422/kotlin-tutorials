package com.bennyhuo.kotlin.advancedtypes.visibilities.core

internal class CoreApiKotlinA {
    @JvmName("%abcd")
    internal fun a(){
        println("Hello A")
    }
}
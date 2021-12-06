package com.bennyhuo.kotlin.advancedtypes.visibilities

import com.bennyhuo.kotlin.advancedtypes.visibilities.core.CoreApiKotlinA
import com.bennyhuo.kotlin.advancedtypes.visibilities.core.CoreApiKotlinB

class ApiKotlin {
    private val coreApiKotlinA = CoreApiKotlinA()
    private val coreApiKotlinB = CoreApiKotlinB()

    fun a(){
        coreApiKotlinA.a()
    }

    fun b(){
        coreApiKotlinB.b()
    }
}

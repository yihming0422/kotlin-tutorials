package com.bennyhuo.kotlin.annotations.apt.compiler

import com.bennyhuo.aptutils.utils.writeToFile
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

class HelloWorldGenerator {
    fun generate(){
        FileSpec.builder("com.bennyhuo.kotlin.annotations", "HelloKapt")
            .addFunction(
                FunSpec.builder("helloWorld")
                    .addCode(CodeBlock.of("println(%S)", "HelloWorld"))
                    .build()
            ).build().writeToFile()
    }
}
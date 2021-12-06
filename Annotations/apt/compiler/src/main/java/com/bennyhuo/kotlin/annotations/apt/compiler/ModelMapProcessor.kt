package com.bennyhuo.kotlin.annotations.apt.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.bennyhuo.aptutils.types.ClassType
import com.bennyhuo.aptutils.types.asKotlinTypeName
import com.bennyhuo.aptutils.types.packageName
import com.bennyhuo.aptutils.types.simpleName
import com.bennyhuo.aptutils.utils.writeToFile
import com.bennyhuo.kotlin.annotations.apt.ModelMap
import com.squareup.kotlinpoet.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy

@SupportedAnnotationTypes("com.bennyhuo.kotlin.annotations.apt.ModelMap")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class ModelMapProcessor: AbstractProcessor() {

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)
    }

    //fun Sample.toMap() = mapOf("a" to a, "b" to b)
//fun <V> Map<String, V>.toSample() = Sample(this["a"] as Int, this["b"] as String)
    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(ModelMap::class.java)
            .forEach {
                element ->
                element.enclosedElements.filterIsInstance<ExecutableElement>()
                    .firstOrNull { it.simpleName() == "<init>" }
                    ?.let {
                        val typeElement = element as TypeElement
                        FileSpec.builder(typeElement.packageName(), "${typeElement.simpleName()}\$\$ModelMap")
                            .addFunction(
                                FunSpec.builder("toMap")
                                    .receiver(typeElement.asType().asKotlinTypeName())
                                    .addStatement("return mapOf(${it.parameters.joinToString {""""${it.simpleName()}" to ${it.simpleName()}""" }})")
                                    .build()
                            )
                            .addFunction(
                                FunSpec.builder("to${typeElement.simpleName()}")
                                    .addTypeVariable(TypeVariableName("V"))
                                    .receiver(MAP.parameterizedBy(STRING, TypeVariableName("V")))
                                    .addStatement(
                                        "return ${typeElement.simpleName()}(${it.parameters.joinToString{ """this["${it.simpleName()}"] as %T """ } })",
                                        *it.parameters.map { it.asType().asKotlinTypeName() }.toTypedArray()
                                    )
                                    .build()
                            )
                            .build().writeToFile()
                    }
            }
        return true
    }
}
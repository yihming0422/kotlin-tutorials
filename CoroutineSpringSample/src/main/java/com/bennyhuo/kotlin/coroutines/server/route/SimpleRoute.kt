package com.bennyhuo.kotlin.coroutines.server.route

import com.bennyhuo.kotlin.coroutines.server.repo.StudentRepository
import kotlinx.coroutines.reactive.asFlow
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter
import org.springframework.web.reactive.function.server.router

@Configuration
class SimpleRoute(val repository: StudentRepository) {

    @Bean
    fun update() = coRouter {
        "/route/students".nest {
            GET("/") { request ->
                repository.findAll()
                    .asFlow().let {
                        ServerResponse.ok().bodyAndAwait(it)
                    }
            }

            GET("/id/{id}") { request ->
                repository.findById(request.pathVariable("id").toLong())
                    .asFlow().let {
                        ServerResponse.ok().bodyAndAwait(it)
                    }
            }

            GET("/name/{name}") { request ->
                repository.findByName(request.pathVariable("name"))
                    .asFlow().let {
                        ServerResponse.ok().bodyAndAwait(it)
                    }
            }
        }
    }

}
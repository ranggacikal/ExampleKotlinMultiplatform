package com.ranggacikal.plugins

import com.apurebase.kgraphql.GraphQL
import graphql.dessertSchema
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {
    install(GraphQL) {
        playground = true
        schema {
            dessertSchema()
        }
    }
//    routing {
//        get("hello") {
//            call.respondText("Hello World!")
//        }
//    }
}

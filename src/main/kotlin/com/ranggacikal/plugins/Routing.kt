package com.ranggacikal.plugins

import com.apurebase.kgraphql.GraphQL
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import di.mainModule
import graphql.dessertSchema
import graphql.reviewSchema
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.core.context.KoinContextHandler
import org.koin.core.context.startKoin
import services.DessertService
import services.ReviewService

fun Application.configureRouting() {
    KoinContextHandler.stop()
    startKoin {
        modules(mainModule)
    }
    install(GraphQL) {
        playground = true
        val dessertService = DessertService()
        val reviewService = ReviewService()
        schema {
            dessertSchema(dessertService)
            reviewSchema(reviewService)
        }
    }
//    routing {
//        get("hello") {
//            call.respondText("Hello World!")
//        }
//    }
}

package com.ranggacikal

import com.apurebase.kgraphql.GraphQL
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.ranggacikal.plugins.*
import di.mainModule
import org.koin.core.context.startKoin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    startKoin {
        modules(mainModule)
    }
    configureRouting()
}

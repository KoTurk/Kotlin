package nl.blue4it.basic

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import nl.blue4it.basic.controller.dto.request.Payment
import io.ktor.server.plugins.contentnegotiation.*
import nl.blue4it.basic.controller.PaymentController
import nl.blue4it.basic.service.PaymentService
import nl.blue4it.basic.service.async.KafkaClient
import nl.blue4it.basic.service.async.RestClient
import nl.blue4it.basic.service.async.SOAPClient

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        routing {
            install(ContentNegotiation) {
                json()
            }
            post("/") {
                val requestBody = call.receive<List<Payment>>()
                println("Received $requestBody")

                PaymentController(PaymentService(KafkaClient(), RestClient(), SOAPClient())).processPayment(requestBody)
                call.respond("Hello from server - received $requestBody")
            }
        }
    }.start(wait = true)
}
package nl.blue4it.basic

import nl.blue4it.basic.controller.PaymentController
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.PaymentService
import nl.blue4it.basic.service.async.KafkaClient
import nl.blue4it.basic.service.async.RestClient
import nl.blue4it.basic.service.async.SOAPClient

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        routing {
            install(ContentNegotiation) {
                json()
            }
            post("/payment") {
                val requestBody = call.receive<List<Payment>>()
                println("Received $requestBody")

                val status = PaymentController(PaymentService(KafkaClient(), SOAPClient(), RestClient())).processPayment(requestBody)
                call.respond(status)
            }
        }
    }.start(wait = true)
}
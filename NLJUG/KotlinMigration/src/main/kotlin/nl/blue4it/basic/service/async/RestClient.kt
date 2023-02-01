package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay

class RestClient {
    suspend fun send(payment: Payment) {
        println("Payment REST")
        delay(5000)
        println("Payment REST executed")
    }
}
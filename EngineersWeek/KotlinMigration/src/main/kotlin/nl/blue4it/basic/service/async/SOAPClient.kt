package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay

class SOAPClient {
    suspend fun send(payment: Payment) {
        println("Payment SOAP")
        delay(2000)
        println("Payment SOAP executed")
    }
}
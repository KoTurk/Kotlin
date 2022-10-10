package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay

class KafkaClient {
    suspend fun send(payment: Payment) {
        println("Payment Kafka")
        delay(1000)
        println("Payment Kafka executed")
    }
}
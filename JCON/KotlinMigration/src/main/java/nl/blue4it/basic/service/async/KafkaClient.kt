package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class KafkaClient {

    suspend fun send(payment: Payment?) { // suspend is like saying, hay I need to wait, let's execute some other stuff now
        println("Payment Kafka")
        delay(2000)
        println("Payment Kafka executed")
    }
}
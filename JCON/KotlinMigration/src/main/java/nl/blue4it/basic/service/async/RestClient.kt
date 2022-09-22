package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay
import nl.blue4it.basic.service.exception.SomeOtherClientException
import org.springframework.stereotype.Component

@Component
class RestClient {
    suspend fun send(payment: Payment?) {
        println("Payment REST")
        delay(1000)
        println("Payment REST executed")
    }
}
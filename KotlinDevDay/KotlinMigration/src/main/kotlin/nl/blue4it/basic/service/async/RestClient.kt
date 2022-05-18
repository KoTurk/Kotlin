package nl.blue4it.basic.service.async

import example.avro.Payment
import nl.blue4it.basic.service.exception.SomeOtherClientException
import org.springframework.stereotype.Component

@Component
class RestClient {
    fun send(payment: Payment?) {
        println("Payment REST")
        throw SomeOtherClientException("Something bad happened")
    }
}
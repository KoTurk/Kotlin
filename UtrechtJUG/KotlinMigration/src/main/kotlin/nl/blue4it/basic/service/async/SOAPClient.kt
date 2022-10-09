package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class SOAPClient {
    suspend fun send(payment: Payment) {
        println("Payment SOAP")
        delay(2000)
        println("Payment SOAP executed")
    }
}
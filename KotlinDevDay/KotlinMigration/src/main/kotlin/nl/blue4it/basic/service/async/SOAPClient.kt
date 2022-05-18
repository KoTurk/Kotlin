package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import nl.blue4it.basic.service.AvroPayment
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class SOAPClient {
    fun send(payment: AvroPayment) {
        println("Payment SOAP")
    }
}
package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaClient {
    suspend fun send(payment: Payment) {
        println("Payment Kafka")
        delay(1000)
        println("Payment Kafka executed")
    }
}
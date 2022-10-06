package nl.blue4it.basic.service.async

import example.avro.Payment
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaClient {
    private val kafkaTemplate: KafkaTemplate<String, Payment>? = null

    fun send(payment: Payment) {
        println("Kafka client started")
        println("Kafka client executed")
    }
}
package nl.blue4it.basic.service.async

import example.avro.Payment
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaClient {
    fun send(payment: Payment) {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            println("InterruptedException")
            throw RuntimeException(e)
        }
        println("Payment Kafka")
    }
}
package nl.blue4it.basic.service.async

import example.avro.Payment
import kotlinx.coroutines.delay
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
@Slf4j
class SOAPClient {
    private val kafkaTemplate: KafkaTemplate<String, Payment>? = null

    suspend fun send(payment: Payment?) {
        println("Payment REST")
        delay(10000L)
        println("Payment RES executedT")
    }
}
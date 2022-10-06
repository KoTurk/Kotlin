package nl.blue4it.basic.service

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.async.KafkaClient
import nl.blue4it.basic.service.async.RestClient
import nl.blue4it.basic.service.async.SOAPClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

typealias AvroPayment = example.avro.Payment
typealias RequestPayment = Payment

@Component
class PaymentService constructor(
    private val client: KafkaClient,
    private val client2: RestClient,
    private val client3: SOAPClient
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(PaymentService::class.java)
    }

    fun processPayment(payments: List<Payment>) {
        payments
            .map { payment: RequestPayment -> payment.mapPayment() }
            .forEach { payment: AvroPayment ->
                kotlin.runCatching {
                                    sendAsync(payment)
                                }.onFailure {
                                    log.info("Exception when handling")
                                }.onSuccess {
                                    log.info("Successfully send payment")
                                }
            }
    }

    private fun RequestPayment.mapPayment(): AvroPayment {
            val duration: Int

            // null check
            this.duration.let {
                log.info("Going to map with  {}", this.duration)

                duration = when (this.duration) {
                    "weekly" -> { 1 }
                    "monthly" -> { 2 }
                    "yearly" -> { 3 }
                    else -> { 0 }
                }
            }.also {
                log.info("creating metrics now")
                // metrics
            } ?: kotlin.run { log.info("No duration set") }

            // type alias
            return AvroPayment(
                name, fromIban, toIban,
                amount, balance, true, duration
            )
        }

    private fun sendAsync(payment: AvroPayment) = runBlocking {
            launch { client2.send(payment) } // launch is used if you don't need the result, async does
            launch { client3.send(payment) }
            launch { client.send(payment) }
    }
}
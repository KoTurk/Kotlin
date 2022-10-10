package nl.blue4it.basic.service

import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.async.KafkaClient
import nl.blue4it.basic.service.async.RestClient
import nl.blue4it.basic.service.async.SOAPClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

typealias RequestPayment = Payment
typealias AvroPayment = example.avro.Payment

class PaymentService(private val client: KafkaClient,
        private val client2: SOAPClient,
        private val client3: RestClient) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(PaymentService::class.java)
    }

    suspend fun processPayment(payments: List<Payment>) {
        payments
            .map { payment -> payment.mapPayment() }
            .forEach { payment: AvroPayment ->
                kotlin.runCatching {
                    sendAsync(payment)
                }.onFailure {
                    log.error("something bad happened")
                }
            }
    }

    private fun RequestPayment.mapPayment(): AvroPayment {
        var durationNumber = 0

        this.duration?.let {
            log.info("Going to map with  {}", this.duration)

            durationNumber = when(this.duration) {
                "weekly" -> 1
                "monthly" -> 2
                "yearly" -> 1
                else -> 0
            }
        }.also {
            // create metric here
        } ?: run {
            log.error("something is going wrong")
        }

        return AvroPayment(
            this.name, this.fromIban, this.toIban,
            this.amount, this.balance, true, durationNumber
        )
    }

    private suspend fun sendAsync(payment: AvroPayment) {
        client.send(payment)
        client2.send(payment)
        client3.send(payment)
    }


}
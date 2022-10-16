package nl.blue4it.basic.service

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.async.AsyncSender
import nl.blue4it.basic.service.exception.FutureException
import nl.blue4it.basic.service.exception.PaymentRejectedException
import nl.blue4it.basic.service.exception.SomeOtherClientException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

typealias RequestPayment = Payment
typealias AvroPayment = example.avro.Payment

@Component
class PaymentService(private val client: AsyncSender) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(PaymentService::class.java)
    }

   // @Throws(PaymentRejectedException::class)
    fun processPayment(payments: List<RequestPayment>) {
        payments
            .map { it.mapPayment() }
            .forEach { payment: AvroPayment ->
                kotlin.runCatching {
                    client.send(payment)
                }.onFailure {
                    log.error("something is going wrong")
                }
            }
    }

    private fun RequestPayment.mapPayment(): AvroPayment {
        var durationNumber = 5

        // can change to switch to when
        this.duration?.let {
            durationNumber = when(it) {
                "weekly" -> 1
                "monthly" -> 2
                "yearly" -> 3
                else -> 0
            }
        }.also {
            // create metrics here
        } ?: kotlin.run { log.info("this is so handy") }

        // type alias
        return AvroPayment(
            this.name, this.fromIban, this.toIban,
            this.amount, this.balance, true, durationNumber
        )
    }
}
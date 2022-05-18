package nl.blue4it.basic.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.async.KafkaClient
import nl.blue4it.basic.service.async.RestClient
import nl.blue4it.basic.service.async.SOAPClient
import nl.blue4it.basic.service.exception.PaymentRejectedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

typealias AvroPayment = example.avro.Payment
typealias RequestPayment = Payment;

@Component
class PaymentService @Autowired constructor(
    private val client: KafkaClient,
    private val client2: RestClient,
    private val client3: SOAPClient
) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(PaymentService::class.java)
    }

    fun processPayment(payments: List<RequestPayment>) {
        payments
            .map { payment: RequestPayment -> mapPayment(payment) }
            .forEach { payment: AvroPayment ->
               kotlin.runCatching {
                   CoroutineScope(Dispatchers.Default).sendAsync(payment)
               }.onFailure {
                   throw PaymentRejectedException("Payment Rejected")
               }.onSuccess {
                   log.info("Successfully send payment")
               }
            }
    }

    private fun CoroutineScope.sendAsync(payment: AvroPayment)  = async() {
        val soap = async { client3.send(payment) }
        val rest = async { client2.send(payment) }
        rest.await()
        val kafka = async { client.send(payment) }
    }

    private fun mapPayment(payment: RequestPayment): AvroPayment {
        val duration: Int

        // null check
        payment.duration.let {
            log.info("Going to map with  {}", payment.duration)

            duration = when (payment.duration) {
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
            payment.name, payment.fromIban, payment.toIban,
            payment.amount, payment.balance, true, duration
        )
    }
}
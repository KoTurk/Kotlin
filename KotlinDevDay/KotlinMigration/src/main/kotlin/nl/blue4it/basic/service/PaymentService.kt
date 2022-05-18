package nl.blue4it.basic.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.async.KafkaClient
import nl.blue4it.basic.service.async.RestClient
import nl.blue4it.basic.service.async.SOAPClient
import nl.blue4it.basic.service.exception.PaymentRejectedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

// as of or whole scope
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

     fun processPayment(payments: List<RequestPayment>) {
        payments
            .map { payment: RequestPayment -> mapPayment(payment) }
            .forEach { payment: AvroPayment ->
               kotlin.runCatching {
                   CoroutineScope(Dispatchers.Default).sendAsync(payment)
               }.onFailure {
                   log.info("Exception when handling")
               }.onSuccess {
                   log.info("Successfully send payment")
               }
            }
    }

    // runblocking, don't do this.
    // Better do this in combination with flows
    private fun CoroutineScope.sendAsync(payment: AvroPayment)  = runBlocking {
        val mq = launch { client2.send(payment) }
        val soap = launch { client3.send(payment) }
        val kafka = launch { client.send(payment) }
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
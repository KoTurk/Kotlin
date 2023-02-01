package nl.blue4it.basic.controller

import io.ktor.http.*
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.PaymentService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PaymentController constructor(private val paymentService: PaymentService) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(PaymentService::class.java)
    }

    suspend fun processPayment( payment: List<Payment>) {
        log.info("Processing payments")

        return kotlin.runCatching {
            paymentService.processPayment(payment)
        }.onSuccess {
            log.info("Payment processed")
        }.onFailure {
            log.info("Got an error when processing payment")
        }.fold(
            { HttpStatusCode.OK },
            { HttpStatusCode.InternalServerError }
        )
    }
}
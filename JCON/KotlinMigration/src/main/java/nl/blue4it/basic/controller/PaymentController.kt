package nl.blue4it.basic.controller

import mu.KotlinLogging
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.PaymentService


class PaymentController constructor(
    private val paymentService: PaymentService
) {
    private val log = KotlinLogging.logger {}

    suspend fun processPayment(payments: List<Payment>): String {
        return kotlin.runCatching {
            log.info("Processing payments")
            paymentService.processPayment(payments)
        }.onFailure {
            log.info("Got an error when processing payment")
            // metrics
        }.onSuccess {
            log.info("Payment processed")
            // metrics
        }.fold(
            {  "Created payment" },
            {  "Something failed" },
        )
    }
}
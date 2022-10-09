package nl.blue4it.basic.controller

import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.PaymentService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController constructor(private val paymentService: PaymentService) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(PaymentService::class.java)
    }

    @PostMapping
    fun processPayment(@RequestBody payment: List<Payment>): ResponseEntity<String> {
        log.info("Processing payments")

        return kotlin.runCatching {
            paymentService.processPayment(payment)
        }.onSuccess {
            log.info("Payment processed")
        }.onFailure {
            log.info("Got an error when processing payment")
        }.fold(
            { ResponseEntity.ok("Created payment") },
            { ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() }
        )
    }
}
package nl.blue4it.basic.controller

import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.PaymentService
import nl.blue4it.basic.service.exception.PaymentRejectedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController @Autowired constructor(
    private val paymentService: PaymentService
) {
    companion object {
        val log: Logger = LoggerFactory.getLogger(PaymentController::class.java)
    }

    @PostMapping
    fun processPayment(@RequestBody payments: List<Payment>): ResponseEntity<String> {
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
            {  ResponseEntity.ok("Created payment") },
            {  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() },
        )
    }
}
package nl.blue4it.basic.controller

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.PaymentService
import nl.blue4it.basic.service.exception.PaymentRejectedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
@RequiredArgsConstructor
class PaymentController constructor(
    private val paymentService: PaymentService
) {
    private val log = KotlinLogging.logger {}

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
package nl.blue4it.basic.controller

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import nl.blue4it.basic.controller.dto.request.Payment
import nl.blue4it.basic.service.PaymentService
import nl.blue4it.basic.service.exception.PaymentRejectedException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class PaymentController constructor(
    private val paymentService: PaymentService
) {

    companion object {
            val log: Logger = LoggerFactory.getLogger(PaymentService::class.java)
        }

    @PostMapping
    fun processPayment(@RequestBody payments: List<Payment>): ResponseEntity<String> {
        log.info("Processing payments")
        return kotlin.runCatching {
                            log.info("Processing payments")
                            paymentService.processPayment(payments)
                        }.fold(
                            {  ResponseEntity.ok("Created payment") },
                            {  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() },
                        )
    }
}
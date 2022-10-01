package nl.blue4it.basic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.blue4it.basic.controller.dto.request.Payment;
import nl.blue4it.basic.service.PaymentService;
import nl.blue4it.basic.service.exception.PaymentRejectedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping
    public ResponseEntity<String> processPayment(@Valid @RequestBody List<Payment> payment) throws PaymentRejectedException {
        log.info("Processing payments");

        try {
            paymentService.processPayment(payment);
            log.info("Payment processed");
            return ResponseEntity.ok("Created payment");
        } catch (PaymentRejectedException ex) {
            log.info("Got an error when processing payment");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

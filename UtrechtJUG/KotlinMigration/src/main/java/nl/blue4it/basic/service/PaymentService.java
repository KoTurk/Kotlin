package nl.blue4it.basic.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.blue4it.basic.controller.dto.request.Payment;
import nl.blue4it.basic.service.async.AsyncSender;
import nl.blue4it.basic.service.exception.FutureException;
import nl.blue4it.basic.service.exception.PaymentRejectedException;
import nl.blue4it.basic.service.exception.SomeOtherClientException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final AsyncSender client;

    public void processPayment(List<Payment> payments) throws PaymentRejectedException {
        // no stream needed
        payments.stream()
                .map(this::mapPayment)
                .forEach(payment -> {
                    try {
                        client.send(payment);
                    } catch (FutureException | SomeOtherClientException e) {
                        // catch problem
                        throw new PaymentRejectedException("");
                    }
                });
    }

    private example.avro.Payment mapPayment(Payment payment) {
        int durationNumber;

        log.info("Going to map with  {}", payment.getDuration());

        // can change to switch to when
        if (payment.getDuration().equals("weekly")) {
            durationNumber = 1;
        } else if (payment.getDuration().equals("monthly")) {
            durationNumber = 2;
        } else if (payment.getDuration().equals("yearly")) {
            durationNumber = 3;
        } else {
            durationNumber = 0;
        }

        // type alias
        return new example.avro.Payment(payment.getName(), payment.getFromIban(), payment.getToIban(),
                payment.getAmount(), payment.getBalance(), true, durationNumber);
    }
}


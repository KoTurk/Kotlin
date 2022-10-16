package nl.blue4it.basic.service.async;

import example.avro.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SOAPClient {
    private final KafkaTemplate<String, Payment> kafkaTemplate;

    public void send(Payment payment) {
        System.out.println("SOAP client started");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            throw new RuntimeException(e);
        }
        System.out.println("SOAP client executed");
    }
}

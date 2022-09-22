package nl.blue4it.basic.service.async;

import example.avro.Payment;
import nl.blue4it.basic.service.exception.SomeOtherClientException;
import org.springframework.stereotype.Component;

@Component
public class RestClient {

    public void send(Payment payment) {
        System.out.println("Rest client started");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
            throw new RuntimeException(e);
        }
        System.out.println("Rest client executed");
    }
}

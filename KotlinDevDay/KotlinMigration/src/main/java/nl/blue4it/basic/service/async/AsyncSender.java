package nl.blue4it.basic.service.async;

import example.avro.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.blue4it.basic.service.exception.FutureException;
import nl.blue4it.basic.service.exception.SomeOtherClientException;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AsyncSender {
    private final KafkaClient client;
    private final RestClient client2;

    private final SOAPClient client3;

    public void send(Payment payment) throws FutureException, SomeOtherClientException {
        log.info("Going to process transaction, sending message {}", payment);

        // async with Java 8
        CompletableFuture<Void> futureOne = CompletableFuture.runAsync(() -> {
            client.send(payment);
        });

        CompletableFuture<Void> futureTwo = CompletableFuture.runAsync(() -> {
            client2.send(payment);
        });

        CompletableFuture<Void> futureThree = CompletableFuture.runAsync(() -> {
            client3.send(payment);
        });

        try {
            CompletableFuture.allOf(futureOne, futureTwo, futureThree).join();
        } catch (CompletionException | SomeOtherClientException e) {
            log.error("Exception when processing", e);
            throw new FutureException("oh no");
        }
    }
}
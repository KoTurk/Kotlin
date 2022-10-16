package nl.blue4it.basic

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class PaymentEngineApplication

fun main(args: Array<String>) {
    runApplication<PaymentEngineApplication>(*args)
}

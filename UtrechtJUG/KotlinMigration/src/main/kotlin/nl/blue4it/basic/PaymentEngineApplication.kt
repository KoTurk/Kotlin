package nl.blue4it.basic

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class PaymentEngineApplication // open is opposite of final, and needs because of @Configuration
fun main(args: Array<String>) {
    runApplication<PaymentEngineApplication>(*args)
}
package nl.blue4it.basic

import nl.blue4it.basic.controller.PaymentController
import nl.blue4it.basic.controller.dto.request.Payment
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import java.util.concurrent.ExecutionException

@SpringBootTest(classes = [PaymentEngineApplication::class])
internal class PaymentControllerTest {
    @Autowired private lateinit var paymentController: PaymentController

    @Test
    fun test() {
        paymentController.processPayment(paymentList)
    }

    private val paymentList: List<Payment>
        get() {
            val paymentList: MutableList<Payment> = ArrayList()
            paymentList.add(
                Payment(
                    "TestUser", "NL63ABNA332454654", "NL61RABO0332454375",
                    100f, 200f, "weekly"
                )
            )
            return paymentList
        }
}
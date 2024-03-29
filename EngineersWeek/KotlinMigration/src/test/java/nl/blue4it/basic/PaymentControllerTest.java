package nl.blue4it.basic;


import nl.blue4it.basic.controller.PaymentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@DirtiesContext
@SpringBootTest(classes = PaymentEngineApplication.class)
class PaymentControllerTest {

    @Autowired
    private PaymentController paymentController;

    @Test
    public void test() throws ExecutionException, InterruptedException {
        paymentController.processPayment(getPaymentList());
    }

    private List<nl.blue4it.basic.controller.dto.request.Payment> getPaymentList() {
        List<nl.blue4it.basic.controller.dto.request.Payment> paymentList = new ArrayList<nl.blue4it.basic.controller.dto.request.Payment>();

        paymentList.add(new nl.blue4it.basic.controller.dto.request.Payment("TestUser", "NL63ABNA332454654", "NL61RABO0332454375",
                100F, 200F, "weekly"  ));

        return paymentList;
    }

}

package vn.edu.fpt.mss.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.fpt.mss.client.dto.PaymentProcessDto;
import vn.edu.fpt.mss.client.dto.PaymentResultDto;

@FeignClient(name = "payment-service", url = "${application.services.payment-service.url:http://localhost:8084}")
public interface PaymentClient {

    @PostMapping("/api/v1/payments/process")
    PaymentResultDto processPayment(@RequestBody PaymentProcessDto request);

    @PostMapping("/api/v1/payments/{id}/refund")
    PaymentResultDto refundPayment(@PathVariable("id") Integer transactionId);
}

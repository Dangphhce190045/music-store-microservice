package vn.edu.fpt.mss.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.mss.dto.request.PaymentProcessRequest;
import vn.edu.fpt.mss.dto.response.PaymentResponse;
import vn.edu.fpt.mss.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for processing payments and querying payment history")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Process a new payment for invoice")
    public PaymentResponse processPayment(@Valid @RequestBody PaymentProcessRequest request) {
        return paymentService.processPayment(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get payment transaction by ID")
    public PaymentResponse getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping
    @Operation(summary = "Get payment transactions by invoiceId or customerId")
    public List<PaymentResponse> getPayments(
            @RequestParam(required = false) Integer invoiceId,
            @RequestParam(required = false) Integer customerId) {
        if (invoiceId != null) {
            return paymentService.getPaymentsByInvoiceId(invoiceId);
        } else if (customerId != null) {
            return paymentService.getPaymentsByCustomerId(customerId);
        }
        throw new IllegalArgumentException("Either invoiceId or customerId must be provided");
    }
}

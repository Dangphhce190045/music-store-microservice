package vn.edu.fpt.mss.service;

import vn.edu.fpt.mss.dto.request.PaymentProcessRequest;
import vn.edu.fpt.mss.dto.response.PaymentResponse;
import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(PaymentProcessRequest request);
    PaymentResponse getPaymentById(Integer transactionId);
    List<PaymentResponse> getPaymentsByInvoiceId(Integer invoiceId);
    List<PaymentResponse> getPaymentsByCustomerId(Integer customerId);
}

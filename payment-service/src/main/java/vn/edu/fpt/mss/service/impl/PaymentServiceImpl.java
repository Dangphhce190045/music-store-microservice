package vn.edu.fpt.mss.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.mss.dto.request.PaymentProcessRequest;
import vn.edu.fpt.mss.dto.response.PaymentResponse;
import vn.edu.fpt.mss.entity.PaymentStatus;
import vn.edu.fpt.mss.entity.PaymentTransaction;
import vn.edu.fpt.mss.common.exception.ResourceNotFoundException;
import vn.edu.fpt.mss.repository.PaymentTransactionRepository;
import vn.edu.fpt.mss.service.PaymentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentTransactionRepository paymentRepository;

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentProcessRequest request) {
        log.info("Processing payment for invoice ID: {}, amount: {}", request.getInvoiceId(), request.getAmount());

        // Mô phỏng cổng thanh toán (Mock Payment Gateway)
        String refCode = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        PaymentTransaction transaction = PaymentTransaction.builder()
                .invoiceId(request.getInvoiceId())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.SUCCESS)
                .referenceCode(refCode)
                .note(request.getNote())
                .build();

        PaymentTransaction saved = paymentRepository.save(transaction);
        log.info("Payment successful, transaction ID: {}, ref: {}", saved.getTransactionId(), refCode);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Integer transactionId) {
        PaymentTransaction transaction = paymentRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment transaction not found with ID: " + transactionId));
        return mapToResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByInvoiceId(Integer invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getPaymentsByCustomerId(Integer customerId) {
        return paymentRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PaymentResponse mapToResponse(PaymentTransaction entity) {
        return PaymentResponse.builder()
                .transactionId(entity.getTransactionId())
                .invoiceId(entity.getInvoiceId())
                .customerId(entity.getCustomerId())
                .amount(entity.getAmount())
                .paymentMethod(entity.getPaymentMethod())
                .status(entity.getStatus())
                .referenceCode(entity.getReferenceCode())
                .note(entity.getNote())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}

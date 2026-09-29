package vn.edu.fpt.mss.service.impl;

import feign.FeignException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.mss.client.CatalogClient;
import vn.edu.fpt.mss.client.CustomerClient;
import vn.edu.fpt.mss.client.LibraryClient;
import vn.edu.fpt.mss.client.PaymentClient;
import vn.edu.fpt.mss.client.dto.CustomerDto;
import vn.edu.fpt.mss.client.dto.GrantEntitlementDto;
import vn.edu.fpt.mss.client.dto.PaymentProcessDto;
import vn.edu.fpt.mss.client.dto.PaymentResultDto;
import vn.edu.fpt.mss.client.dto.TrackDto;
import vn.edu.fpt.mss.dto.request.CheckoutItemRequest;
import vn.edu.fpt.mss.dto.request.CheckoutRequest;
import vn.edu.fpt.mss.dto.request.InvoiceLineRequest;
import vn.edu.fpt.mss.dto.request.InvoiceRequest;
import vn.edu.fpt.mss.dto.response.CheckoutResponse;
import vn.edu.fpt.mss.dto.response.InvoiceLineResponse;
import vn.edu.fpt.mss.dto.response.InvoiceResponse;
import vn.edu.fpt.mss.entity.Invoice;
import vn.edu.fpt.mss.entity.InvoiceLine;
import vn.edu.fpt.mss.common.exception.ResourceNotFoundException;
import vn.edu.fpt.mss.repository.InvoiceRepository;
import vn.edu.fpt.mss.service.InvoiceService;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerClient customerClient;
    private final CatalogClient catalogClient;
    private final PaymentClient paymentClient;
    private final LibraryClient libraryClient;

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> findAll() {
        return invoiceRepository.findAllByOrderByInvoiceDateDesc().stream()
                .map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> findByCustomerId(Integer customerId) {
        return invoiceRepository.findByCustomerIdOrderByInvoiceDateDesc(customerId).stream()
                .map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse findById(Integer id) {
        return toResponse(findEntity(id));
    }

    @Override
    @Transactional
    public InvoiceResponse create(InvoiceRequest request) {
        // 1. Verify customer existence & fetch official names via FeignClient
        CustomerDto customer = fetchCustomer(request.getCustomerId());

        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setCustomerFirstName(customer.getFirstName());
        invoice.setCustomerLastName(customer.getLastName());
        invoice.setInvoiceDate(request.getInvoiceDate() == null ? LocalDateTime.now() : request.getInvoiceDate());
        invoice.setBillingAddress(request.getBillingAddress());
        invoice.setBillingCity(request.getBillingCity());
        invoice.setBillingState(request.getBillingState());
        invoice.setBillingCountry(request.getBillingCountry());
        invoice.setBillingPostalCode(request.getBillingPostalCode());

        // 2. Verify each track via FeignClient & calculate accurate total
        BigDecimal calculatedTotal = BigDecimal.ZERO;
        if (request.getLines() != null && !request.getLines().isEmpty()) {
            for (InvoiceLineRequest lineReq : request.getLines()) {
                TrackDto track = fetchTrack(lineReq.getTrackId());

                BigDecimal unitPrice = lineReq.getUnitPrice() != null ? lineReq.getUnitPrice() : track.getUnitPrice();
                InvoiceLine line = InvoiceLine.builder()
                        .trackId(track.getTrackId())
                        .trackName(track.getName())
                        .unitPrice(unitPrice)
                        .quantity(lineReq.getQuantity())
                        .build();
                invoice.addLine(line);
                BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(lineReq.getQuantity()));
                calculatedTotal = calculatedTotal.add(subTotal);
            }
        }

        invoice.setTotal(request.getTotal() != null ? request.getTotal() : calculatedTotal);
        return toResponse(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional
    public InvoiceResponse update(Integer id, InvoiceRequest request) {
        Invoice invoice = findEntity(id);

        CustomerDto customer = fetchCustomer(request.getCustomerId());
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setCustomerFirstName(customer.getFirstName());
        invoice.setCustomerLastName(customer.getLastName());

        if (request.getInvoiceDate() != null) {
            invoice.setInvoiceDate(request.getInvoiceDate());
        }
        invoice.setBillingAddress(request.getBillingAddress());
        invoice.setBillingCity(request.getBillingCity());
        invoice.setBillingState(request.getBillingState());
        invoice.setBillingCountry(request.getBillingCountry());
        invoice.setBillingPostalCode(request.getBillingPostalCode());

        if (request.getLines() != null) {
            invoice.getLines().clear();
            BigDecimal calculatedTotal = BigDecimal.ZERO;
            for (InvoiceLineRequest lineReq : request.getLines()) {
                TrackDto track = fetchTrack(lineReq.getTrackId());

                BigDecimal unitPrice = lineReq.getUnitPrice() != null ? lineReq.getUnitPrice() : track.getUnitPrice();
                InvoiceLine line = InvoiceLine.builder()
                        .trackId(track.getTrackId())
                        .trackName(track.getName())
                        .unitPrice(unitPrice)
                        .quantity(lineReq.getQuantity())
                        .build();
                invoice.addLine(line);
                BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(lineReq.getQuantity()));
                calculatedTotal = calculatedTotal.add(subTotal);
            }
            invoice.setTotal(request.getTotal() != null ? request.getTotal() : calculatedTotal);
        } else if (request.getTotal() != null) {
            invoice.setTotal(request.getTotal());
        }

        return toResponse(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        invoiceRepository.delete(findEntity(id));
    }

    @Override
    public CheckoutResponse checkout(CheckoutRequest request) {
        // ── STEP 1: Validate customer ────────────────────────────────────────
        CustomerDto customer = fetchCustomer(request.getCustomerId());
        log.info("[Checkout] Customer verified: id={} name={} {}",
                customer.getCustomerId(), customer.getFirstName(), customer.getLastName());

        // ── STEP 2: Validate tracks & build invoice lines ─────────────────
        BigDecimal total = BigDecimal.ZERO;
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setCustomerFirstName(customer.getFirstName());
        invoice.setCustomerLastName(customer.getLastName());
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setBillingAddress(request.getBillingAddress());
        invoice.setBillingCity(request.getBillingCity());
        invoice.setBillingState(request.getBillingState());
        invoice.setBillingCountry(request.getBillingCountry());
        invoice.setBillingPostalCode(request.getBillingPostalCode());
        invoice.setInvoiceCode("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        invoice.setPaymentStatus("PENDING");
        invoice.setPaymentMethod(request.getPaymentMethod());

        for (CheckoutItemRequest item : request.getItems()) {
            TrackDto track = fetchTrack(item.getTrackId());
            BigDecimal subTotal = track.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            InvoiceLine line = InvoiceLine.builder()
                    .trackId(track.getTrackId())
                    .trackName(track.getName())
                    .unitPrice(track.getUnitPrice())
                    .quantity(item.getQuantity())
                    .build();
            invoice.addLine(line);
            total = total.add(subTotal);
        }
        invoice.setTotal(total);
        Invoice savedInvoice = invoiceRepository.save(invoice);
        log.info("[Checkout] Invoice created: id={}, code={}, total={}",
                savedInvoice.getInvoiceId(), savedInvoice.getInvoiceCode(), total);

        // ── STEP 3: Process payment with Saga Failure handling ───────────────
        PaymentProcessDto paymentRequest = PaymentProcessDto.builder()
                .invoiceId(savedInvoice.getInvoiceId())
                .customerId(customer.getCustomerId())
                .amount(total)
                .paymentMethod(request.getPaymentMethod())
                .note(request.getPaymentNote())
                .build();

        PaymentResultDto paymentResult;
        try {
            paymentResult = paymentClient.processPayment(paymentRequest);
            log.info("[Checkout] Payment successful: ref={}, transactionId={}",
                    paymentResult.getReferenceCode(), paymentResult.getTransactionId());
        } catch (Exception ex) {
            log.error("[Checkout Saga] Payment failed for invoiceId={}: {}", savedInvoice.getInvoiceId(), ex.getMessage());
            savedInvoice.setPaymentStatus("PAYMENT_FAILED");
            invoiceRepository.save(savedInvoice);
            throw new RuntimeException("Payment processing failed: " + ex.getMessage(), ex);
        }

        // ── STEP 4: Update invoice to PAID ───────────────────────────────────
        savedInvoice.setPaymentStatus("PAID");
        savedInvoice.setPaymentTransactionId(String.valueOf(paymentResult.getTransactionId()));
        savedInvoice.setPaidAt(LocalDateTime.now());
        invoiceRepository.save(savedInvoice);

        // ── STEP 5: Grant entitlements with Saga Compensating Transaction ───
        List<Integer> grantedTrackIds = new ArrayList<>();
        try {
            for (InvoiceLine line : savedInvoice.getLines()) {
                libraryClient.grantEntitlement(GrantEntitlementDto.builder()
                        .customerId(customer.getCustomerId())
                        .trackId(line.getTrackId())
                        .invoiceId(savedInvoice.getInvoiceId())
                        .build());
                grantedTrackIds.add(line.getTrackId());
                log.info("[Checkout] Entitlement granted: customerId={}, trackId={}",
                        customer.getCustomerId(), line.getTrackId());
            }
        } catch (Exception ex) {
            log.error("[Checkout Saga Compensation] Entitlement grant failed: {}. Triggering refund for transactionId={}",
                    ex.getMessage(), paymentResult.getTransactionId());
            // Compensating action 1: Refund via payment-service
            try {
                paymentClient.refundPayment(paymentResult.getTransactionId());
                log.info("[Checkout Saga Compensation] Refund executed successfully for transactionId={}", paymentResult.getTransactionId());
            } catch (Exception refundEx) {
                log.error("[Checkout Saga Compensation] Refund execution failed: {}", refundEx.getMessage());
            }
            // Compensating action 2: Mark invoice as REFUNDED
            savedInvoice.setPaymentStatus("REFUNDED");
            invoiceRepository.save(savedInvoice);
            throw new RuntimeException("Checkout failed during entitlement granting. Payment has been refunded: " + ex.getMessage(), ex);
        }

        // ── STEP 6: Build response ───────────────────────────────────────────
        final Integer currentInvoiceId = savedInvoice.getInvoiceId();
        List<InvoiceLineResponse> lineResponses = savedInvoice.getLines().stream()
                .map(l -> InvoiceLineResponse.builder()
                        .invoiceLineId(l.getInvoiceLineId())
                        .invoiceId(currentInvoiceId)
                        .trackId(l.getTrackId())
                        .trackName(l.getTrackName())
                        .unitPrice(l.getUnitPrice())
                        .quantity(l.getQuantity())
                        .subTotal(l.getUnitPrice().multiply(BigDecimal.valueOf(l.getQuantity())))
                        .build())
                .toList();

        return CheckoutResponse.builder()
                .invoiceId(savedInvoice.getInvoiceId())
                .invoiceCode(savedInvoice.getInvoiceCode())
                .customerId(savedInvoice.getCustomerId())
                .customerFirstName(savedInvoice.getCustomerFirstName())
                .customerLastName(savedInvoice.getCustomerLastName())
                .total(savedInvoice.getTotal())
                .paymentStatus(savedInvoice.getPaymentStatus())
                .paymentTransactionId(savedInvoice.getPaymentTransactionId())
                .paymentReferenceCode(paymentResult.getReferenceCode())
                .paidAt(savedInvoice.getPaidAt())
                .lines(lineResponses)
                .grantedTrackIds(grantedTrackIds)
                .build();
    }

    private CustomerDto fetchCustomer(Integer customerId) {
        try {
            return customerClient.findById(customerId);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Customer", customerId);
        }
    }

    private TrackDto fetchTrack(Integer trackId) {
        try {
            return catalogClient.findTrackById(trackId);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("Track", trackId);
        }
    }

    private Invoice findEntity(Integer id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", id));
    }

    private InvoiceResponse toResponse(Invoice invoice) {
        List<InvoiceLineResponse> lineResponses = new ArrayList<>();
        if (invoice.getLines() != null) {
            for (InvoiceLine line : invoice.getLines()) {
                lineResponses.add(InvoiceLineResponse.builder()
                        .invoiceLineId(line.getInvoiceLineId())
                        .invoiceId(invoice.getInvoiceId())
                        .trackId(line.getTrackId())
                        .trackName(line.getTrackName())
                        .unitPrice(line.getUnitPrice())
                        .quantity(line.getQuantity())
                        .subTotal(line.getUnitPrice().multiply(BigDecimal.valueOf(line.getQuantity())))
                        .build());
            }
        }

        return InvoiceResponse.builder()
                .invoiceId(invoice.getInvoiceId())
                .customerId(invoice.getCustomerId())
                .customerFirstName(invoice.getCustomerFirstName())
                .customerLastName(invoice.getCustomerLastName())
                .invoiceDate(invoice.getInvoiceDate())
                .billingAddress(invoice.getBillingAddress())
                .billingCity(invoice.getBillingCity())
                .billingState(invoice.getBillingState())
                .billingCountry(invoice.getBillingCountry())
                .billingPostalCode(invoice.getBillingPostalCode())
                .total(invoice.getTotal())
                .lines(lineResponses)
                .build();
    }
}

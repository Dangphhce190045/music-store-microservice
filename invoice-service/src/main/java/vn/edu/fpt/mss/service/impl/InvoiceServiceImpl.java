package vn.edu.fpt.mss.service.impl;

import feign.FeignException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.mss.client.CatalogClient;
import vn.edu.fpt.mss.client.CustomerClient;
import vn.edu.fpt.mss.client.dto.CustomerDto;
import vn.edu.fpt.mss.client.dto.TrackDto;
import vn.edu.fpt.mss.dto.request.InvoiceLineRequest;
import vn.edu.fpt.mss.dto.request.InvoiceRequest;
import vn.edu.fpt.mss.dto.response.InvoiceLineResponse;
import vn.edu.fpt.mss.dto.response.InvoiceResponse;
import vn.edu.fpt.mss.entity.Invoice;
import vn.edu.fpt.mss.entity.InvoiceLine;
import vn.edu.fpt.mss.common.exception.ResourceNotFoundException;
import vn.edu.fpt.mss.repository.InvoiceRepository;
import vn.edu.fpt.mss.service.InvoiceService;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerClient customerClient;
    private final CatalogClient catalogClient;

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

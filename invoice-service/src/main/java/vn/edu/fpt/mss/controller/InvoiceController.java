package vn.edu.fpt.mss.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.mss.dto.request.InvoiceRequest;
import vn.edu.fpt.mss.dto.response.InvoiceResponse;
import vn.edu.fpt.mss.service.InvoiceService;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoices", description = "CRUD operations for invoices and order lines")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    @Operation(summary = "Get all invoices, optionally filtered by customerId")
    public List<InvoiceResponse> findAll(@RequestParam(required = false) Integer customerId) {
        return customerId == null ? invoiceService.findAll() : invoiceService.findByCustomerId(customerId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get invoice details by id with all invoice lines")
    public InvoiceResponse findById(@PathVariable Integer id) {
        return invoiceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create an invoice with invoice lines and automatic total calculation")
    public InvoiceResponse create(@Valid @RequestBody InvoiceRequest request) {
        return invoiceService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an invoice")
    public InvoiceResponse update(@PathVariable Integer id, @Valid @RequestBody InvoiceRequest request) {
        return invoiceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an invoice")
    public void delete(@PathVariable Integer id) {
        invoiceService.delete(id);
    }
}

package vn.edu.fpt.mss.service;

import java.util.List;
import vn.edu.fpt.mss.dto.request.InvoiceRequest;
import vn.edu.fpt.mss.dto.response.InvoiceResponse;

public interface InvoiceService {

    List<InvoiceResponse> findAll();

    List<InvoiceResponse> findByCustomerId(Integer customerId);

    InvoiceResponse findById(Integer id);

    InvoiceResponse create(InvoiceRequest request);

    InvoiceResponse update(Integer id, InvoiceRequest request);

    void delete(Integer id);
}

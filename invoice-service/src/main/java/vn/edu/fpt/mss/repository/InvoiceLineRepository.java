package vn.edu.fpt.mss.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.mss.entity.InvoiceLine;

@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Integer> {

    List<InvoiceLine> findByInvoiceInvoiceId(Integer invoiceId);

    List<InvoiceLine> findByTrackId(Integer trackId);
}

package vn.edu.fpt.mss.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.mss.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findByCustomerIdOrderByInvoiceDateDesc(Integer customerId);

    List<Invoice> findAllByOrderByInvoiceDateDesc();
}

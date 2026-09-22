package vn.edu.fpt.mss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.mss.entity.PaymentTransaction;
import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer> {
    List<PaymentTransaction> findByInvoiceId(Integer invoiceId);
    List<PaymentTransaction> findByCustomerId(Integer customerId);
}

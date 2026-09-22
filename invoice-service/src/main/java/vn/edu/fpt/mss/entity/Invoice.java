package vn.edu.fpt.mss.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.fpt.mss.common.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Invoice")
public class Invoice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceId")
    private Integer invoiceId;

    @Column(name = "InvoiceCode", length = 50)
    private String invoiceCode;

    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    @Column(name = "CustomerFirstName", length = 40)
    private String customerFirstName;

    @Column(name = "CustomerLastName", length = 20)
    private String customerLastName;

    @Column(name = "InvoiceDate", nullable = false)
    private LocalDateTime invoiceDate;

    @Column(name = "BillingAddress", length = 70)
    private String billingAddress;

    @Column(name = "BillingCity", length = 40)
    private String billingCity;

    @Column(name = "BillingState", length = 40)
    private String billingState;

    @Column(name = "BillingCountry", length = 40)
    private String billingCountry;

    @Column(name = "BillingPostalCode", length = 10)
    private String billingPostalCode;

    @Column(name = "SubTotal", precision = 10, scale = 2)
    private BigDecimal subTotal;

    @Builder.Default
    @Column(name = "DiscountAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "TaxAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "Total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Builder.Default
    @Column(name = "PaymentStatus", nullable = false, length = 30)
    private String paymentStatus = "PAID";

    @Builder.Default
    @Column(name = "PaymentMethod", length = 50)
    private String paymentMethod = "CREDIT_CARD";

    @Column(name = "PaymentTransactionId", length = 100)
    private String paymentTransactionId;

    @Column(name = "PaidAt")
    private LocalDateTime paidAt;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<InvoiceLine> lines = new ArrayList<>();

    public void addLine(InvoiceLine line) {
        lines.add(line);
        line.setInvoice(this);
    }

    public void removeLine(InvoiceLine line) {
        lines.remove(line);
        line.setInvoice(null);
    }
}

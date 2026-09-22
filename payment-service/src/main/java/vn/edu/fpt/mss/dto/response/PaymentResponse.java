package vn.edu.fpt.mss.dto.response;

import lombok.*;
import vn.edu.fpt.mss.entity.PaymentMethod;
import vn.edu.fpt.mss.entity.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Integer transactionId;
    private Integer invoiceId;
    private Integer customerId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String referenceCode;
    private String note;
    private LocalDateTime createdAt;
}

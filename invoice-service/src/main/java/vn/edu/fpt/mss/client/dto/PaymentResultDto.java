package vn.edu.fpt.mss.client.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResultDto {

    private Integer transactionId;
    private Integer invoiceId;
    private Integer customerId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private String referenceCode;
    private String note;
    private LocalDateTime createdAt;
}

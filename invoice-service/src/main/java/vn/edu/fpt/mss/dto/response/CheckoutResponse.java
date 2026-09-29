package vn.edu.fpt.mss.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponse {

    private Integer invoiceId;
    private String invoiceCode;
    private Integer customerId;
    private String customerFirstName;
    private String customerLastName;
    private BigDecimal total;
    private String paymentStatus;
    private String paymentTransactionId;
    private String paymentReferenceCode;
    private LocalDateTime paidAt;
    private List<InvoiceLineResponse> lines;
    private List<Integer> grantedTrackIds;
}

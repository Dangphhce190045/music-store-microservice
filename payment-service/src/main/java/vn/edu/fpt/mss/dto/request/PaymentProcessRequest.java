package vn.edu.fpt.mss.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import vn.edu.fpt.mss.entity.PaymentMethod;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentProcessRequest {

    @NotNull(message = "Invoice ID cannot be null")
    private Integer invoiceId;

    @NotNull(message = "Customer ID cannot be null")
    private Integer customerId;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private String note;
}

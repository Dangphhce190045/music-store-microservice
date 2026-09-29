package vn.edu.fpt.mss.client.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentProcessDto {

    @NotNull
    private Integer invoiceId;

    @NotNull
    private Integer customerId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;

    @NotNull
    private String paymentMethod;

    private String note;
}

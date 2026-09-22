package vn.edu.fpt.mss.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceRequest {

    @NotNull(message = "CustomerId is required")
    private Integer customerId;

    @Size(max = 40, message = "CustomerFirstName must not exceed 40 characters")
    private String customerFirstName;

    @Size(max = 20, message = "CustomerLastName must not exceed 20 characters")
    private String customerLastName;

    private LocalDateTime invoiceDate;

    @Size(max = 70)
    private String billingAddress;

    @Size(max = 40)
    private String billingCity;

    @Size(max = 40)
    private String billingState;

    @Size(max = 40)
    private String billingCountry;

    @Size(max = 10)
    private String billingPostalCode;

    private BigDecimal total;

    @Valid
    private List<InvoiceLineRequest> lines;
}

package vn.edu.fpt.mss.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
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
public class InvoiceLineRequest {

    @NotNull(message = "TrackId is required")
    private Integer trackId;

    @Size(max = 200, message = "TrackName must not exceed 200 characters")
    private String trackName;

    @NotNull(message = "UnitPrice is required")
    @DecimalMin(value = "0.0", message = "UnitPrice must be greater than or equal to 0")
    private BigDecimal unitPrice;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}

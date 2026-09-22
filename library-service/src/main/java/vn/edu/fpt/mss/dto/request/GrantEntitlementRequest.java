package vn.edu.fpt.mss.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrantEntitlementRequest {

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotNull(message = "Track ID is required")
    private Integer trackId;

    @NotNull(message = "Invoice ID is required")
    private Integer invoiceId;
}

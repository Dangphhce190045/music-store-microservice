package vn.edu.fpt.mss.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequest {

    @NotNull(message = "CustomerId is required")
    private Integer customerId;

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

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    private String paymentNote;

    @Valid
    @NotNull(message = "At least one track is required")
    @Size(min = 1, message = "At least one track is required")
    private List<CheckoutItemRequest> items;
}

package vn.edu.fpt.mss.client.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrantEntitlementDto {

    private Integer customerId;
    private Integer trackId;
    private Integer invoiceId;
}

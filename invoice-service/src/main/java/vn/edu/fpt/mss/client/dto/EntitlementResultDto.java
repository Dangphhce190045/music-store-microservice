package vn.edu.fpt.mss.client.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntitlementResultDto {

    private Integer entitlementId;
    private Integer customerId;
    private Integer trackId;
    private Integer invoiceId;
}

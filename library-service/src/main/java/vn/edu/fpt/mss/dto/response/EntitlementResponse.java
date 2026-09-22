package vn.edu.fpt.mss.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntitlementResponse {
    private Integer entitlementId;
    private Integer customerId;
    private Integer trackId;
    private Integer invoiceId;
    private LocalDateTime grantedAt;
}

package vn.edu.fpt.mss.service;

import vn.edu.fpt.mss.dto.request.GrantEntitlementRequest;
import vn.edu.fpt.mss.dto.response.EntitlementResponse;
import java.util.List;

public interface EntitlementService {
    EntitlementResponse grantEntitlement(GrantEntitlementRequest request);
    List<EntitlementResponse> getEntitlementsByCustomerId(Integer customerId);
    boolean hasEntitlement(Integer customerId, Integer trackId);
}

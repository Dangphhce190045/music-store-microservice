package vn.edu.fpt.mss.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import vn.edu.fpt.mss.client.dto.GrantEntitlementDto;
import vn.edu.fpt.mss.client.dto.EntitlementResultDto;

@FeignClient(name = "library-service", url = "${application.services.library-service.url:http://localhost:8085}")
public interface LibraryClient {

    @PostMapping("/api/v1/entitlements/grant")
    EntitlementResultDto grantEntitlement(@RequestBody GrantEntitlementDto request);
}

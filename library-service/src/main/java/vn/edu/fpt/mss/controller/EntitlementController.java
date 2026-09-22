package vn.edu.fpt.mss.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.mss.dto.request.GrantEntitlementRequest;
import vn.edu.fpt.mss.dto.response.EntitlementResponse;
import vn.edu.fpt.mss.service.EntitlementService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entitlements")
@RequiredArgsConstructor
@Tag(name = "Entitlements", description = "Track ownership and digital rights management APIs")
public class EntitlementController {

    private final EntitlementService entitlementService;

    @PostMapping("/grant")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Grant track ownership to a customer after purchase")
    public EntitlementResponse grantEntitlement(@Valid @RequestBody GrantEntitlementRequest request) {
        return entitlementService.grantEntitlement(request);
    }

    @GetMapping
    @Operation(summary = "Get all owned tracks (library) for a customer")
    public List<EntitlementResponse> getEntitlements(@RequestParam Integer customerId) {
        return entitlementService.getEntitlementsByCustomerId(customerId);
    }

    @GetMapping("/check")
    @Operation(summary = "Check if customer has permission to stream/download a track")
    public boolean checkEntitlement(@RequestParam Integer customerId, @RequestParam Integer trackId) {
        return entitlementService.hasEntitlement(customerId, trackId);
    }
}

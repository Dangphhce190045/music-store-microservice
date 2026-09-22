package vn.edu.fpt.mss.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.fpt.mss.dto.request.GrantEntitlementRequest;
import vn.edu.fpt.mss.dto.response.EntitlementResponse;
import vn.edu.fpt.mss.entity.Entitlement;
import vn.edu.fpt.mss.repository.EntitlementRepository;
import vn.edu.fpt.mss.service.EntitlementService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntitlementServiceImpl implements EntitlementService {

    private final EntitlementRepository entitlementRepository;

    @Override
    @Transactional
    public EntitlementResponse grantEntitlement(GrantEntitlementRequest request) {
        log.info("Granting entitlement: customer={}, track={}, invoice={}",
                request.getCustomerId(), request.getTrackId(), request.getInvoiceId());

        return entitlementRepository.findByCustomerIdAndTrackId(request.getCustomerId(), request.getTrackId())
                .map(this::mapToResponse)
                .orElseGet(() -> {
                    Entitlement entitlement = Entitlement.builder()
                            .customerId(request.getCustomerId())
                            .trackId(request.getTrackId())
                            .invoiceId(request.getInvoiceId())
                            .build();
                    Entitlement saved = entitlementRepository.save(entitlement);
                    return mapToResponse(saved);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntitlementResponse> getEntitlementsByCustomerId(Integer customerId) {
        return entitlementRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasEntitlement(Integer customerId, Integer trackId) {
        return entitlementRepository.existsByCustomerIdAndTrackId(customerId, trackId);
    }

    private EntitlementResponse mapToResponse(Entitlement entity) {
        return EntitlementResponse.builder()
                .entitlementId(entity.getEntitlementId())
                .customerId(entity.getCustomerId())
                .trackId(entity.getTrackId())
                .invoiceId(entity.getInvoiceId())
                .grantedAt(entity.getGrantedAt())
                .build();
    }
}

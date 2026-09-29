package vn.edu.fpt.mss.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

/**
 * FallbackController — Xử lý circuit breaker fallback responses.
 *
 * <p>Khi một downstream service không available, Gateway sẽ forward
 * request đến đây thay vì trả về lỗi 500 không có thông tin.
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/catalog")
    public Mono<ResponseEntity<Map<String, Object>>> catalogFallback() {
        return buildFallback("catalog-service");
    }

    @GetMapping("/customer")
    public Mono<ResponseEntity<Map<String, Object>>> customerFallback() {
        return buildFallback("customer-service");
    }

    @GetMapping("/invoice")
    public Mono<ResponseEntity<Map<String, Object>>> invoiceFallback() {
        return buildFallback("invoice-service");
    }

    @GetMapping("/payment")
    public Mono<ResponseEntity<Map<String, Object>>> paymentFallback() {
        return buildFallback("payment-service");
    }

    @GetMapping("/library")
    public Mono<ResponseEntity<Map<String, Object>>> libraryFallback() {
        return buildFallback("library-service");
    }

    private Mono<ResponseEntity<Map<String, Object>>> buildFallback(String serviceName) {
        Map<String, Object> body = Map.of(
            "timestamp", Instant.now().toString(),
            "status", HttpStatus.SERVICE_UNAVAILABLE.value(),
            "error", "Service Temporarily Unavailable",
            "service", serviceName,
            "message", String.format(
                "The %s is currently unavailable. Please try again in a few moments.", serviceName
            )
        );
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body));
    }
}

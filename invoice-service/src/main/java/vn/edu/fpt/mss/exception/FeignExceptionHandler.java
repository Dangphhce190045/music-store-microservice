package vn.edu.fpt.mss.exception;

import feign.FeignException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.edu.fpt.mss.common.dto.ApiResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FeignExceptionHandler {

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ApiResponse<Object>> handleFeignNotFound(FeignException.NotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Target resource not found in external service"));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse<Object>> handleFeignException(FeignException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.error(HttpStatus.SERVICE_UNAVAILABLE.value(), "External service communication error: " + ex.getMessage()));
    }
}

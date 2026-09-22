package vn.edu.fpt.mss.common.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.edu.fpt.mss.common.dto.ApiResponse;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle ResourceNotFoundException and return 404")
    void shouldHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Track", 55L);
        ResponseEntity<ApiResponse<Object>> response = exceptionHandler.handleBaseException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Track not found with id: 55", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Should handle IllegalArgumentException and return 400")
    void shouldHandleIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid state requested");
        ResponseEntity<ApiResponse<Object>> response = exceptionHandler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Invalid state requested", response.getBody().getMessage());
    }

    @Test
    @DisplayName("Should handle generic unhandled Exception and return 500")
    void shouldHandleGenericException() {
        Exception ex = new RuntimeException("Unexpected database outage");
        ResponseEntity<ApiResponse<Object>> response = exceptionHandler.handleGeneric(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Internal server error", response.getBody().getMessage());
    }
}

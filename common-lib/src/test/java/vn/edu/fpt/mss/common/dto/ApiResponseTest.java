package vn.edu.fpt.mss.common.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    @DisplayName("Should create success response with default message")
    void shouldCreateSuccessResponseWithDefaultMessage() {
        ApiResponse<String> response = ApiResponse.success("test-data");

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("Success", response.getMessage());
        assertEquals("test-data", response.getData());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Should create success response with custom message")
    void shouldCreateSuccessResponseWithCustomMessage() {
        ApiResponse<Integer> response = ApiResponse.success("Operation completed", 42);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("Operation completed", response.getMessage());
        assertEquals(42, response.getData());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Should create error response without details")
    void shouldCreateErrorResponseWithoutDetails() {
        ApiResponse<Void> response = ApiResponse.error(404, "Not found");

        assertNotNull(response);
        assertEquals(404, response.getStatus());
        assertEquals("Not found", response.getMessage());
        assertNull(response.getData());
        assertNull(response.getErrors());
    }

    @Test
    @DisplayName("Should create error response with error details list")
    void shouldCreateErrorResponseWithDetails() {
        List<String> errors = List.of("field1 is required", "field2 must be positive");
        ApiResponse<Void> response = ApiResponse.error(400, "Validation failed", errors);

        assertNotNull(response);
        assertEquals(400, response.getStatus());
        assertEquals("Validation failed", response.getMessage());
        assertNull(response.getData());
        assertNotNull(response.getErrors());
        assertEquals(errors, response.getErrors());
    }
}

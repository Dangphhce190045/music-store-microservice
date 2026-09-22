package vn.edu.fpt.mss.common.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    @DisplayName("Should format message correctly when given resource and key")
    void shouldFormatMessageWithResourceAndKey() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Album", 101L);
        assertEquals("Album not found with id: 101", ex.getMessage());
    }

    @Test
    @DisplayName("Should format message correctly when given resource, field, and value")
    void shouldFormatMessageWithResourceFieldAndValue() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Customer", "email", "john@example.com");
        assertEquals("Customer not found with email: john@example.com", ex.getMessage());
    }

    @Test
    @DisplayName("Should use raw message when constructor with message string is used")
    void shouldUseRawMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Custom not found message");
        assertEquals("Custom not found message", ex.getMessage());
    }
}

package vn.edu.fpt.mss.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s not found with %s: %s", resource, field, value), HttpStatus.NOT_FOUND);
    }
}

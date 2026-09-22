package vn.edu.fpt.mss.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Object key) {
        super(resource + " not found with identifier: " + key);
    }
}

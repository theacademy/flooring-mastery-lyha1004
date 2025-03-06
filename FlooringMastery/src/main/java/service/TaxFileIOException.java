package service;

public class TaxFileIOException extends RuntimeException {
    public TaxFileIOException(String message) {
        super(message);
    }
    public TaxFileIOException(String message, Throwable cause) {
        super(message, cause);
    }
}

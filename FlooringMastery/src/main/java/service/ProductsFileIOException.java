package service;

public class ProductsFileIOException extends RuntimeException {
    public ProductsFileIOException(String message) {
        super(message);
    }
    public ProductsFileIOException(String message, Throwable cause) {
        super(message, cause);
    }
}

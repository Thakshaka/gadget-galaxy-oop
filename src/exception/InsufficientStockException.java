package exception;

/**
 * Custom Exception for stock-related errors.
 * Demonstrates advanced EXCEPTION HANDLING.
 */
public class InsufficientStockException extends Exception {
    public InsufficientStockException(String message) {
        super(message);
    }
}

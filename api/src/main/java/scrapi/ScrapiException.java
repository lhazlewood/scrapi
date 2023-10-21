package scrapi;

/**
 * Root class of all SCRAPI exceptions. Subclasses pertain to specific error conditions that can be independently
 * caught based on error-handling preferences.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
public class ScrapiException extends RuntimeException {

    /**
     * Creates a new instance with the specified explanation message.
     *
     * @param message the message explaining why the exception is thrown.
     */
    public ScrapiException(String message) {
        super(message);
    }

    /**
     * Creates a new instance with the specified explanation message and underlying cause.
     *
     * @param message the message explaining why the exception is thrown.
     * @param cause   the underlying cause that resulted in this exception being thrown.
     */
    public ScrapiException(String message, Throwable cause) {
        super(message, cause);
    }
}

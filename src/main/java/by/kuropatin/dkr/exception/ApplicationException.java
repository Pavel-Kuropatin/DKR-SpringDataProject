package by.kuropatin.dkr.exception;

public class ApplicationException extends RuntimeException {

    public ApplicationException(final Exception e) {
        super(e);
    }

    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, final Object... args) {
        super(String.format(message, args));
    }
}
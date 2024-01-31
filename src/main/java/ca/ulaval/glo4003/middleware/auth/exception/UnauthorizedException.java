package ca.ulaval.glo4003.middleware.auth.exception;

public abstract class UnauthorizedException extends RuntimeException {

    private static final String ERROR_MESSAGE =
        "You are not authorized to access this resource : %s";

    protected UnauthorizedException(String message) {
        super(String.format(ERROR_MESSAGE, message));
    }
}

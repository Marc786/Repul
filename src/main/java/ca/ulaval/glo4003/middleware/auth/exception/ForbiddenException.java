package ca.ulaval.glo4003.middleware.auth.exception;

public class ForbiddenException extends RuntimeException {

    private static final String ERROR_MESSAGE =
        "You are not allowed to access this resource";

    public ForbiddenException() {
        super(ERROR_MESSAGE);
    }
}

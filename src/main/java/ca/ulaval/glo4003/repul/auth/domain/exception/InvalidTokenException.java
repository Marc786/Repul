package ca.ulaval.glo4003.repul.auth.domain.exception;

import ca.ulaval.glo4003.middleware.auth.exception.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {

    private static final String ERROR_MESSAGE = "Invalid token, log in first";

    public InvalidTokenException() {
        super(ERROR_MESSAGE);
    }
}

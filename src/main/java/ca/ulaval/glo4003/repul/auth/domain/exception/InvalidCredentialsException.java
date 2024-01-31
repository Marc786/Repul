package ca.ulaval.glo4003.repul.auth.domain.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidCredentialsException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "Invalid credentials";

    public InvalidCredentialsException() {
        super(ERROR_MESSAGE);
    }
}

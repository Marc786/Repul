package ca.ulaval.glo4003.repul.auth.domain.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidRoleException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "The role provided is not valid.";

    public InvalidRoleException() {
        super(ERROR_MESSAGE);
    }
}

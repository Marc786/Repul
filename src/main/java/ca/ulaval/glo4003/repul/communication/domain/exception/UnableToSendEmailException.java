package ca.ulaval.glo4003.repul.communication.domain.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class UnableToSendEmailException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "Email cannot be sent";

    public UnableToSendEmailException() {
        super(ERROR_MESSAGE);
    }
}

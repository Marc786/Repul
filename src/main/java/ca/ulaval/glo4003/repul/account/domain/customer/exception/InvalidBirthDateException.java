package ca.ulaval.glo4003.repul.account.domain.customer.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidBirthDateException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "The birth date is invalid.";

    public InvalidBirthDateException() {
        super(ERROR_MESSAGE);
    }
}

package ca.ulaval.glo4003.repul.account.domain.customer.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidULavalEmailException extends InvalidInputException {

    public static final String ERROR_MESSAGE = "The email does not end with @ulaval.ca";

    public InvalidULavalEmailException() {
        super(ERROR_MESSAGE);
    }
}

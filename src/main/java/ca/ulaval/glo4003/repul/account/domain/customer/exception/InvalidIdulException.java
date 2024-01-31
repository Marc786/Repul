package ca.ulaval.glo4003.repul.account.domain.customer.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidIdulException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "The customerId is invalid";

    public InvalidIdulException() {
        super(ERROR_MESSAGE);
    }
}

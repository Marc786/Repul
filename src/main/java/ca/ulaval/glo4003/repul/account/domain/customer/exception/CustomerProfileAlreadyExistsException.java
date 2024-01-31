package ca.ulaval.glo4003.repul.account.domain.customer.exception;

import ca.ulaval.glo4003.exception.ItemAlreadyExistsException;

public class CustomerProfileAlreadyExistsException extends ItemAlreadyExistsException {

    private static final String ERROR_MESSAGE = "The customer already exists";

    public CustomerProfileAlreadyExistsException() {
        super(ERROR_MESSAGE);
    }
}

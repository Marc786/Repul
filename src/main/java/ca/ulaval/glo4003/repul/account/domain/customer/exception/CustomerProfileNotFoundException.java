package ca.ulaval.glo4003.repul.account.domain.customer.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;

public class CustomerProfileNotFoundException extends ItemNotFoundException {

    private static final String ERROR_MESSAGE = "Customers does not exist";

    public CustomerProfileNotFoundException() {
        super(ERROR_MESSAGE);
    }
}

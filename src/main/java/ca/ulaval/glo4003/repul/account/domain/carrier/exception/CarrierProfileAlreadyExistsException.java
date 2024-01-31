package ca.ulaval.glo4003.repul.account.domain.carrier.exception;

import ca.ulaval.glo4003.exception.ItemAlreadyExistsException;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;

public class CarrierProfileAlreadyExistsException extends ItemAlreadyExistsException {

    private static final String ERROR_MESSAGE = "Carrier with email %s already exists";

    public CarrierProfileAlreadyExistsException(EmailAddress email) {
        super(String.format(ERROR_MESSAGE, email.toString()));
    }
}

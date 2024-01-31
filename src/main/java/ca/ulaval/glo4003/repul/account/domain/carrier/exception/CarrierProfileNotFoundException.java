package ca.ulaval.glo4003.repul.account.domain.carrier.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;

public class CarrierProfileNotFoundException extends ItemNotFoundException {

    private static final String ERROR_MESSAGE = "Carrier with email %s not found";

    public CarrierProfileNotFoundException(EmailAddress email) {
        super(String.format(ERROR_MESSAGE, email));
    }
}

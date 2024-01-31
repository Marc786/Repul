package ca.ulaval.glo4003.repul.account.domain.cook.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;

public class CookProfileNotFoundException extends ItemNotFoundException {

    private static final String ERROR_MESSAGE = "Cook with email %s does not exist";

    public CookProfileNotFoundException(EmailAddress email) {
        super(String.format(ERROR_MESSAGE, email));
    }
}

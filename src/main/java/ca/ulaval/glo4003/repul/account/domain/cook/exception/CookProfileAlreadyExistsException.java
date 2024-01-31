package ca.ulaval.glo4003.repul.account.domain.cook.exception;

import ca.ulaval.glo4003.exception.ItemAlreadyExistsException;

public class CookProfileAlreadyExistsException extends ItemAlreadyExistsException {

    private static final String ERROR_MESSAGE = "Cook already exists";

    public CookProfileAlreadyExistsException() {
        super(ERROR_MESSAGE);
    }
}

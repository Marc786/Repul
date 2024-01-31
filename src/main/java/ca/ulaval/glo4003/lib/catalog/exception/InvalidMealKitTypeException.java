package ca.ulaval.glo4003.lib.catalog.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidMealKitTypeException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "Invalid meal kit type";

    public InvalidMealKitTypeException() {
        super(ERROR_MESSAGE);
    }
}

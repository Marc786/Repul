package ca.ulaval.glo4003.lib.date.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidDateFormatException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "Invalid date format";

    public InvalidDateFormatException() {
        super(ERROR_MESSAGE);
    }
}

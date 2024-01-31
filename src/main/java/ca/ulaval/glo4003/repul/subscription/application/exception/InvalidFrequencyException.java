package ca.ulaval.glo4003.repul.subscription.application.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidFrequencyException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "Frequency %s not found";

    public InvalidFrequencyException(String frequency) {
        super(String.format(ERROR_MESSAGE, frequency));
    }
}

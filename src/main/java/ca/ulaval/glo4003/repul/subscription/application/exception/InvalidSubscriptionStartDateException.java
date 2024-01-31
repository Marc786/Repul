package ca.ulaval.glo4003.repul.subscription.application.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidSubscriptionStartDateException extends InvalidInputException {

    private static final String ERROR_MESSAGE =
        "Invalid subscription start date: %s, the start date must not be outside a semester or in the past.";

    public InvalidSubscriptionStartDateException(String date) {
        super(String.format(ERROR_MESSAGE, date));
    }
}

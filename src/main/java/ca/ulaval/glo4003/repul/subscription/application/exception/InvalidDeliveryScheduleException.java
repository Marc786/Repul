package ca.ulaval.glo4003.repul.subscription.application.exception;

import jakarta.ws.rs.BadRequestException;

public class InvalidDeliveryScheduleException extends BadRequestException {

    private static final String ERROR_MESSAGE = "Invalid delivery schedule: %s";

    public InvalidDeliveryScheduleException(String message) {
        super(String.format(ERROR_MESSAGE, message));
    }
}

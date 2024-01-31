package ca.ulaval.glo4003.lib.pickup_point_location.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidPickupPointLocationException extends InvalidInputException {

    private static final String ERROR_MESSAGE = "Pickup point location %s is invalid";

    public InvalidPickupPointLocationException(String location) {
        super(String.format(ERROR_MESSAGE, location));
    }
}

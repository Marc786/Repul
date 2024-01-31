package ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class MealKitCancelledException extends InvalidInputException {

    private static final String ERROR_MESSAGE =
        "Meal kit has been cancelled due to confirmation delay expired";

    public MealKitCancelledException() {
        super(ERROR_MESSAGE);
    }
}

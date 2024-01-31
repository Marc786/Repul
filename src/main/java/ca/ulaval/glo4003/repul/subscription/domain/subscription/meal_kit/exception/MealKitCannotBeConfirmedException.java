package ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class MealKitCannotBeConfirmedException extends InvalidInputException {

    private static final String ERROR_MESSAGE =
        "Meal kit was already confirmed or cancelled";

    public MealKitCannotBeConfirmedException() {
        super(ERROR_MESSAGE);
    }
}

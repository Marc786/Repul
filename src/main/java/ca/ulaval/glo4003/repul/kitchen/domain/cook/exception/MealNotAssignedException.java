package ca.ulaval.glo4003.repul.kitchen.domain.cook.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;

public class MealNotAssignedException extends InvalidInputException {

    public MealNotAssignedException(MealId mealId) {
        super(String.format("Meal with id %s is not assigned", mealId));
    }
}

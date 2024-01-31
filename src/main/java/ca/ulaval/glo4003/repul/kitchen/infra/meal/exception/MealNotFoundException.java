package ca.ulaval.glo4003.repul.kitchen.infra.meal.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;

public class MealNotFoundException extends ItemNotFoundException {

    public MealNotFoundException(MealId mealId) {
        super(String.format("Meal with id %s was not found", mealId));
    }
}

package ca.ulaval.glo4003.repul.kitchen.domain.cook;

import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;

public interface MealFinder {
    Meal findById(MealId mealId);
}

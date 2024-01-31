package ca.ulaval.glo4003.repul.subscription.domain;

import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;

public interface MealClient {
    void addMealToPrepare(MealKit mealKit);
}

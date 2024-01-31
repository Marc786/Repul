package ca.ulaval.glo4003.repul.kitchen.domain.meal;

import ca.ulaval.glo4003.repul.kitchen.domain.cook.MealFinder;
import java.util.List;

public interface MealRepository extends MealFinder {
    void save(Meal meal);

    Meal findById(MealId mealId);

    List<Meal> findAll();
}

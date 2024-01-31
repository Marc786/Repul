package ca.ulaval.glo4003.repul.kitchen.domain.meal;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import java.time.LocalDate;
import java.util.List;

public class Meals {

    private final List<Meal> meals;

    public Meals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public List<Ingredient> getIngredients() {
        return meals.stream().flatMap(meal -> meal.getIngredients().stream()).toList();
    }

    public List<Meal> filterByDeliveryDate(LocalDate startDate, LocalDate endDate) {
        return meals
            .stream()
            .filter(meal ->
                meal.getDeliveryDate().isAfter(startDate) &&
                meal.getDeliveryDate().isBefore(endDate)
            )
            .toList();
    }
}

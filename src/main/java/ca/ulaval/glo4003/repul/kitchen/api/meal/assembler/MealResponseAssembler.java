package ca.ulaval.glo4003.repul.kitchen.api.meal.assembler;

import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealsResponse;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import java.util.List;

public class MealResponseAssembler {

    private final RecipeResponseAssembler recipeResponseAssembler =
        new RecipeResponseAssembler();

    public MealsResponse toResponse(List<Meal> meals) {
        return new MealsResponse(meals.stream().map(this::toResponse).toList());
    }

    private MealResponse toResponse(Meal meal) {
        return new MealResponse(
            meal.getMealId().toString(),
            meal.getMealPreparationStatus().toString(),
            meal.getDeliveryDate().toString(),
            recipeResponseAssembler.toResponse(meal.getRecipes())
        );
    }
}

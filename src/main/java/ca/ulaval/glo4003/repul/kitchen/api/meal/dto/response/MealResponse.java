package ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response;

import java.util.List;

public record MealResponse(
    String mealId,
    String mealPreparationStatus,
    String deliveryDate,
    List<RecipeResponse> recipes
) {}

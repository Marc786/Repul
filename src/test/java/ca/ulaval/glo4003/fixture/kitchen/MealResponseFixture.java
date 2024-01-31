package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.lib.catalog.MealKitCatalog;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.finder.MealRecipeFinder;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.RecipeResponse;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meals;
import java.util.ArrayList;
import java.util.List;

public class MealResponseFixture {

    private final Meals meals = new MealsFixture().build();
    private Meal meal = meals.getMeals().get(0);
    private static final MealRecipeFinder mealRecipeFinder = new MealKitCatalog();
    private List<Recipe> recipes = mealRecipeFinder.findRecipes(MealKitType.STANDARD);
    private final List<RecipeResponse> recipeResponses = new ArrayList<>();

    public MealResponse build() {
        for (Recipe recipe : recipes) {
            recipeResponses.add(new RecipeResponseFixture().withRecipe(recipe).build());
        }

        return new MealResponse(
            meal.getMealId().toString(),
            meal.getMealPreparationStatus().toString(),
            meal.getDeliveryDate().toString(),
            recipeResponses
        );
    }

    public MealResponseFixture withMeal(Meal meal) {
        this.meal = meal;
        this.recipes = meal.getRecipes();
        return this;
    }
}

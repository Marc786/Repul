package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientsResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.RecipeResponse;

public class RecipeResponseFixture {

    private final RecipeFixture recipeFixture = new RecipeFixture();
    private final Recipe recipe = recipeFixture.build();
    private final IngredientsResponseFixture ingredientsResponseFixture =
        new IngredientsResponseFixture();
    private IngredientsResponse ingredientsResponse = ingredientsResponseFixture.build();
    private String recipeName = recipe.name();

    public RecipeResponse build() {
        return new RecipeResponse(recipeName, ingredientsResponse);
    }

    public RecipeResponseFixture withRecipe(Recipe recipe) {
        this.recipeName = recipe.name();
        this.ingredientsResponse =
            ingredientsResponseFixture.withIngredients(recipe.ingredients()).build();
        return this;
    }
}

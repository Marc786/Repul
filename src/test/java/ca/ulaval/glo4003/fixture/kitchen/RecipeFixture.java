package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import java.util.List;

public class RecipeFixture {

    private final IngredientFixture ingredientFixture = new IngredientFixture();
    private final Ingredient ingredient = ingredientFixture.build();

    private final String recipeName = "recipe number 1";
    private final int calories = 1;

    public Recipe build() {
        return new Recipe(recipeName, calories, List.of(ingredient));
    }
}

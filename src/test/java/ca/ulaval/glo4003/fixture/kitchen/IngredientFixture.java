package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Unit;

public class IngredientFixture {

    private final String ingredientName = "ingredient number 1";
    private final int ingredientQuantity = 1;
    private final Ingredient ingredient = new Ingredient(
        ingredientName,
        ingredientQuantity,
        Unit.GRAM
    );

    public Ingredient build() {
        return ingredient;
    }
}

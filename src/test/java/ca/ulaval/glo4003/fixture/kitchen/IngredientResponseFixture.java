package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Unit;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientResponse;
import java.util.Locale;

public class IngredientResponseFixture {

    private String ingredientName = "ingredient number 2";
    private double ingredientQuantity = 5.0;
    private Unit ingredientUnit = Unit.GRAM;

    public IngredientResponse build() {
        return new IngredientResponse(
            ingredientName,
            combineQuantityAndUnits(ingredientQuantity, ingredientUnit)
        );
    }

    public IngredientResponseFixture withIngredient(Ingredient ingredient) {
        this.ingredientName = ingredient.name();
        this.ingredientQuantity = ingredient.quantity();
        this.ingredientUnit = ingredient.unit();
        return this;
    }

    private String combineQuantityAndUnits(double quantityDouble, Unit unit) {
        if (quantityDouble == (int) quantityDouble) {
            return (int) quantityDouble + unit.getValue();
        }
        return String.format(Locale.US, "%.1f", quantityDouble) + unit.getValue();
    }
}

package ca.ulaval.glo4003.repul.kitchen.api.meal.assembler;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Unit;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientsResponse;
import java.util.List;
import java.util.Locale;

public class IngredientResponseAssembler {

    public IngredientsResponse toResponse(List<Ingredient> ingredients) {
        return new IngredientsResponse(
            ingredients.stream().map(this::toResponse).toList()
        );
    }

    private IngredientResponse toResponse(Ingredient ingredient) {
        String quantityWithUnits = combineQuantityAndUnits(
            ingredient.quantity(),
            ingredient.unit()
        );
        return new IngredientResponse(ingredient.name(), quantityWithUnits);
    }

    private String combineQuantityAndUnits(double quantityDouble, Unit unit) {
        if (quantityDouble == (int) quantityDouble) {
            return (int) quantityDouble + unit.getValue();
        }
        return String.format(Locale.US, "%.1f", quantityDouble) + unit.getValue();
    }
}

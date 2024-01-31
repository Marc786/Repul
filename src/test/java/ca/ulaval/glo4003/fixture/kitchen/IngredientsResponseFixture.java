package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientsResponse;
import java.util.ArrayList;
import java.util.List;

public class IngredientsResponseFixture {

    private final IngredientResponseFixture ingredientResponseFixture =
        new IngredientResponseFixture();
    private final IngredientResponse ingredientResponse =
        ingredientResponseFixture.build();
    private List<IngredientResponse> ingredientResponses = new ArrayList<>(
        List.of(ingredientResponse)
    );

    public IngredientsResponse build() {
        return new IngredientsResponse(ingredientResponses);
    }

    public IngredientsResponseFixture withIngredients(List<Ingredient> ingredients) {
        ingredientResponses = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientResponses.add(
                ingredientResponseFixture.withIngredient(ingredient).build()
            );
        }
        return this;
    }
}

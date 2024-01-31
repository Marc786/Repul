package ca.ulaval.glo4003.repul.kitchen.api.meal.assembler;

import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.RecipeResponse;
import java.util.List;

public class RecipeResponseAssembler {

    private final IngredientResponseAssembler ingredientResponseAssembler =
        new IngredientResponseAssembler();

    public List<RecipeResponse> toResponse(List<Recipe> recipes) {
        return recipes.stream().map(this::toResponse).toList();
    }

    private RecipeResponse toResponse(Recipe recipe) {
        return new RecipeResponse(
            recipe.name(),
            ingredientResponseAssembler.toResponse(recipe.ingredients())
        );
    }
}

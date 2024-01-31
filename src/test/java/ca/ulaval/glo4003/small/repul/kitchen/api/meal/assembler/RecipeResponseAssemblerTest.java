package ca.ulaval.glo4003.small.repul.kitchen.api.meal.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.lib.catalog.recipe.Unit;
import ca.ulaval.glo4003.repul.kitchen.api.meal.assembler.RecipeResponseAssembler;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientsResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.RecipeResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeResponseAssemblerTest {

    private static final String INGREDIENT_NAME = "ingredient name";
    private static final int INGREDIENT_QUANTITY = 1;
    private static final Ingredient INGREDIENT = new Ingredient(
        INGREDIENT_NAME,
        INGREDIENT_QUANTITY,
        Unit.GRAM
    );
    private static final List<Ingredient> INGREDIENTS = List.of(INGREDIENT);
    private static final IngredientResponse INGREDIENT_RESPONSE = new IngredientResponse(
        INGREDIENT_NAME,
        INGREDIENT_QUANTITY + Unit.GRAM.getValue()
    );
    private static final IngredientsResponse INGREDIENT_RESPONSES =
        new IngredientsResponse(List.of(INGREDIENT_RESPONSE));
    private static final String RECIPE_NAME = "recipe name";
    private static final int RECIPE_CALORIES = 1;
    private static final Recipe RECIPE = new Recipe(
        RECIPE_NAME,
        RECIPE_CALORIES,
        INGREDIENTS
    );
    private static final List<Recipe> RECIPES = List.of(RECIPE);
    private static final RecipeResponse RECIPE_RESPONSE = new RecipeResponse(
        RECIPE_NAME,
        INGREDIENT_RESPONSES
    );
    private static final List<RecipeResponse> RECIPE_RESPONSES = List.of(RECIPE_RESPONSE);

    private RecipeResponseAssembler recipeResponseAssembler;

    @BeforeEach
    void setup() {
        recipeResponseAssembler = new RecipeResponseAssembler();
    }

    @Test
    void toResponse_returnsRecipeResponse() {
        List<RecipeResponse> actualRecipeResponses = recipeResponseAssembler.toResponse(
            RECIPES
        );

        assertEquals(RECIPE_RESPONSES, actualRecipeResponses);
    }
}

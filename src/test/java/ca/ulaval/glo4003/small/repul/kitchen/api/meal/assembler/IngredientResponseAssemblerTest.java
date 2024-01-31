package ca.ulaval.glo4003.small.repul.kitchen.api.meal.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.kitchen.IngredientFixture;
import ca.ulaval.glo4003.fixture.kitchen.IngredientsResponseFixture;
import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.repul.kitchen.api.meal.assembler.IngredientResponseAssembler;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientsResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IngredientResponseAssemblerTest {

    private static final Ingredient INGREDIENT = new IngredientFixture().build();
    private static final List<Ingredient> INGREDIENTS = List.of(INGREDIENT);
    private static final IngredientsResponse INGREDIENTS_RESPONSE =
        new IngredientsResponseFixture().withIngredients(INGREDIENTS).build();

    private IngredientResponseAssembler ingredientResponseAssembler;

    @BeforeEach
    void setUp() {
        ingredientResponseAssembler = new IngredientResponseAssembler();
    }

    @Test
    void toResponse_returnsIngredientsResponse() {
        IngredientsResponse actualIngredientsResponse =
            ingredientResponseAssembler.toResponse(INGREDIENTS);

        assertEquals(INGREDIENTS_RESPONSE, actualIngredientsResponse);
    }
}

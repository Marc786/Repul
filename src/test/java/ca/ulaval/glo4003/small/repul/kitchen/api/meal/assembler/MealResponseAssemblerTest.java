package ca.ulaval.glo4003.small.repul.kitchen.api.meal.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.lib.catalog.recipe.Unit;
import ca.ulaval.glo4003.repul.kitchen.api.meal.assembler.MealResponseAssembler;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.*;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealResponseAssemblerTest {

    private static final String MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final MealId MEAL_ID = new MealId(MEAL_ID_STRING);
    private static final LocalDate DELIVERY_DATE = LocalDate.of(2020, 3, 3);
    private static final String RECIPE_NAME = "recipe";
    private static final int RECIPE_CALORIES = 100;
    private static final String INGREDIENT_NAME = "ingredient";
    private static final int INGREDIENT_QUANTITY = 1;
    private static final Ingredient INGREDIENT = new Ingredient(
        INGREDIENT_NAME,
        INGREDIENT_QUANTITY,
        Unit.GRAM
    );
    private static final Recipe RECIPE = new Recipe(
        RECIPE_NAME,
        RECIPE_CALORIES,
        List.of(INGREDIENT)
    );
    private static final Meal MEAL = new Meal(MEAL_ID, DELIVERY_DATE, List.of(RECIPE));
    private static final IngredientResponse INGREDIENT_RESPONSE = new IngredientResponse(
        INGREDIENT_NAME,
        INGREDIENT_QUANTITY + Unit.GRAM.getValue()
    );
    private static final IngredientsResponse INGREDIENTS_RESPONSE =
        new IngredientsResponse(List.of(INGREDIENT_RESPONSE));
    private static final RecipeResponse RECIPE_RESPONSE = new RecipeResponse(
        RECIPE_NAME,
        INGREDIENTS_RESPONSE
    );
    private static final MealResponse MEAL_RESPONSE = new MealResponse(
        MEAL_ID_STRING,
        MEAL.getMealPreparationStatus().toString(),
        DELIVERY_DATE.toString(),
        List.of(RECIPE_RESPONSE)
    );

    private MealResponseAssembler mealResponseAssembler;

    @BeforeEach
    void setup() {
        mealResponseAssembler = new MealResponseAssembler();
    }

    @Test
    void toResponse_returnsMealsResponse() {
        MealsResponse expectedResponse = new MealsResponse(List.of(MEAL_RESPONSE));

        MealsResponse actualResponse = mealResponseAssembler.toResponse(List.of(MEAL));

        assertEquals(expectedResponse, actualResponse);
    }
}

package ca.ulaval.glo4003.small.repul.kitchen.domain.meal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.lib.catalog.recipe.Unit;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealPreparationStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealTest {

    private static final String MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final MealId MEAL_ID = new MealId(MEAL_ID_STRING);
    private static final LocalDate DELIVERY_DATE = LocalDate.of(2020, 3, 3);
    private static final String INGREDIENT_NAME = "name";
    private static final int INGREDIENT_QUANTITY = 1;
    private static final Ingredient INGREDIENT = new Ingredient(
        INGREDIENT_NAME,
        INGREDIENT_QUANTITY,
        Unit.GRAM
    );
    private static final List<Ingredient> INGREDIENTS = List.of(INGREDIENT);
    private static final String RECIPES_NAME = "name";
    private static final int RECIPE_CALORIES = 1;
    private static final Recipe RECIPE = new Recipe(
        RECIPES_NAME,
        RECIPE_CALORIES,
        INGREDIENTS
    );
    private static final List<Recipe> RECIPES = List.of(RECIPE);

    private Meal meal;

    @BeforeEach
    void setup() {
        meal = new Meal(MEAL_ID, DELIVERY_DATE, RECIPES);
    }

    @Test
    void assemble_shouldSetMealPreparationStatusToAssembled() {
        meal.assemble();

        Assertions.assertEquals(
            MealPreparationStatus.ASSEMBLED,
            meal.getMealPreparationStatus()
        );
    }

    @Test
    void disassemble_shouldSetMealPreparationStatusToOrderReceived() {
        meal.disassemble();

        assertEquals(
            MealPreparationStatus.ORDER_RECEIVED,
            meal.getMealPreparationStatus()
        );
    }

    @Test
    void getIngredients_shouldReturnIngredientsFromRecipes() {
        List<Ingredient> actualIngredients = meal.getIngredients();

        assertEquals(INGREDIENTS, actualIngredients);
    }
}

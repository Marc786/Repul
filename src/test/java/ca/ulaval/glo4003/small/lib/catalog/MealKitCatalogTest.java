package ca.ulaval.glo4003.small.lib.catalog;

import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.lib.catalog.MealKitCatalog;
import ca.ulaval.glo4003.lib.catalog.MealKitInfo;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.exception.InvalidMealKitTypeException;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.lib.catalog.recipe.StandardMealKitRecipes;
import ca.ulaval.glo4003.lib.value_object.Amount;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealKitCatalogTest {

    private static final Amount AMOUNT = new Amount(75);
    private static final List<Recipe> RECIPES = StandardMealKitRecipes.RECIPES;
    private static final MealKitInfo MEAL_KIT_INFO = new MealKitInfo(RECIPES, AMOUNT);
    private MealKitCatalog mealKitCatalog;

    @BeforeEach
    void setup() {
        mealKitCatalog = new MealKitCatalog();
    }

    @Test
    void nullMealKitType_getMealKitInfo_throwsInvalidMealKitTypeException() {
        assertThrows(
            InvalidMealKitTypeException.class,
            () -> mealKitCatalog.getMealKitInfo(null)
        );
    }

    @Test
    void getMealKitInfo_returnsExpectedMealKitInfo() {
        MealKitInfo actualMealKitInfo = mealKitCatalog.getMealKitInfo(
            MealKitType.STANDARD
        );

        Assertions.assertEquals(MEAL_KIT_INFO, actualMealKitInfo);
    }
}

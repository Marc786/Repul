package ca.ulaval.glo4003.repul.kitchen.domain.meal;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.finder.MealRecipeFinder;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import java.time.LocalDate;
import java.util.List;

public class MealFactory {

    private final MealRecipeFinder mealRecipeFinder;

    public MealFactory(MealRecipeFinder mealRecipeFinder) {
        this.mealRecipeFinder = mealRecipeFinder;
    }

    public Meal create(MealId mealId, MealKitType mealKitType, LocalDate deliveryDate) {
        List<Recipe> recipes = mealRecipeFinder.findRecipes(mealKitType);

        return new Meal(mealId, deliveryDate, recipes);
    }
}

package ca.ulaval.glo4003.lib.catalog.finder;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import java.util.List;

public interface MealRecipeFinder {
    List<Recipe> findRecipes(MealKitType type);
}

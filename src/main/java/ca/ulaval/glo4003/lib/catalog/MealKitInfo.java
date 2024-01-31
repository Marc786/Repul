package ca.ulaval.glo4003.lib.catalog;

import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.lib.value_object.Amount;
import java.util.List;

public record MealKitInfo(List<Recipe> recipes, Amount price) {}

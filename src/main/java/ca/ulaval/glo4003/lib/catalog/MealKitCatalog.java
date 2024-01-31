package ca.ulaval.glo4003.lib.catalog;

import ca.ulaval.glo4003.lib.catalog.exception.InvalidMealKitTypeException;
import ca.ulaval.glo4003.lib.catalog.finder.MealKitPriceFinder;
import ca.ulaval.glo4003.lib.catalog.finder.MealRecipeFinder;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.lib.catalog.recipe.StandardMealKitRecipes;
import ca.ulaval.glo4003.lib.value_object.Amount;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MealKitCatalog implements MealRecipeFinder, MealKitPriceFinder {

    public static final int STANDARD_PRICE = 75;
    private final Map<MealKitType, MealKitInfo> mealKits = new EnumMap<>(
        MealKitType.class
    );

    public MealKitCatalog() {
        initMealKits();
    }

    public MealKitInfo getMealKitInfo(MealKitType mealKitType) {
        MealKitInfo mealKitInfo = mealKits.get(mealKitType);
        if (mealKitInfo == null) {
            throw new InvalidMealKitTypeException();
        }
        return mealKitInfo;
    }

    @Override
    public Amount findPrice(MealKitType type) {
        return getMealKitInfo(type).price();
    }

    @Override
    public List<Recipe> findRecipes(MealKitType type) {
        return getMealKitInfo(type).recipes();
    }

    private void initMealKits() {
        mealKits.put(
            MealKitType.STANDARD,
            new MealKitInfo(StandardMealKitRecipes.RECIPES, new Amount(STANDARD_PRICE))
        );
    }
}

package ca.ulaval.glo4003.lib.catalog.finder;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.value_object.Amount;

public interface MealKitPriceFinder {
    Amount findPrice(MealKitType type);
}

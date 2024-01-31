package ca.ulaval.glo4003.small.lib.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.exception.InvalidMealKitTypeException;
import org.junit.jupiter.api.Test;

class MealKitTypeTest {

    private static final String MEAL_KIT_TYPE = "STANDARD";
    private static final String INVALID_MEAL_KIT_TYPE = "Cummins 6.7L turbo diesel";

    @Test
    void validMealKitType_fromString_returnsValidMealKitType() {
        MealKitType expectedMealKitType = MealKitType.STANDARD;

        MealKitType actualMealKitType = MealKitType.fromString(MEAL_KIT_TYPE);

        assertEquals(expectedMealKitType, actualMealKitType);
    }

    @Test
    void invalidMealKitType_fromString_throwsInvalidMealKitTypeException() {
        assertThrows(
            InvalidMealKitTypeException.class,
            () -> MealKitType.fromString(INVALID_MEAL_KIT_TYPE)
        );
    }
}

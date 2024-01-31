package ca.ulaval.glo4003.lib.catalog;

import ca.ulaval.glo4003.lib.catalog.exception.InvalidMealKitTypeException;
import java.util.Arrays;

public enum MealKitType {
    STANDARD("standard");

    private final String value;

    MealKitType(String value) {
        this.value = value;
    }

    public static MealKitType fromString(String input) {
        return Arrays
            .stream(values())
            .filter(type -> type.value.equalsIgnoreCase(input))
            .findFirst()
            .orElseThrow(InvalidMealKitTypeException::new);
    }

    @Override
    public String toString() {
        return value;
    }
}

package ca.ulaval.glo4003.small.repul.subscription.domain.subscription.meal_kit;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class MealKitIdTest {

    private final String UUID_STRING = UUID.randomUUID().toString();

    @Test
    void twoNewMealKitId_shouldGenerateDifferentUUID() {
        MealKitId mealKitId = new MealKitId();
        MealKitId otherMealKitId = new MealKitId();

        assertNotEquals(mealKitId, otherMealKitId);
    }

    @Test
    void fromString_returnsWithSameUUID() {
        MealKitId mealKitId = new MealKitId(UUID_STRING);

        assertEquals(UUID_STRING, mealKitId.toString());
    }
}

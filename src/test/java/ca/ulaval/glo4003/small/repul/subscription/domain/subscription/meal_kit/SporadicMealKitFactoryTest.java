package ca.ulaval.glo4003.small.repul.subscription.domain.subscription.meal_kit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.SporadicMealKitFactory;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class SporadicMealKitFactoryTest {

    private static final MealKitId MEAL_KIT_ID = new MealKitId();
    private static final LocalDate DELIVERY_DATE = LocalDate.of(2020, 1, 1);
    private final SporadicMealKitFactory sporadicMealKitFactory =
        new SporadicMealKitFactory();

    @Test
    void create_mealKitHasNoConfirmationDelay() {
        MealKit mealKit = sporadicMealKitFactory.create(
            MEAL_KIT_ID,
            DELIVERY_DATE,
            PickupPointLocation.DESJARDINS,
            MealKitType.STANDARD
        );

        int actualConfirmationDelayInDays = mealKit.getConfirmationDelayInDays();
        assertEquals(0, actualConfirmationDelayInDays);
    }
}

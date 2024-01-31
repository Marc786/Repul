package ca.ulaval.glo4003.small.repul.subscription.domain.subscription.meal_kit;

import static ca.ulaval.glo4003.constant.Constants.Subscription.MEAL_KIT_PREPARATION_DELAY_IN_DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.RecurringMealKitFactory;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class RecurringMealKitFactoryTest {

    private static final MealKitId MEAL_KIT_ID = new MealKitId();
    private static final LocalDate DELIVERY_DATE = LocalDate.of(2020, 1, 1);
    private final RecurringMealKitFactory recurringMealKitFactory =
        new RecurringMealKitFactory();

    @Test
    void create_mealKitHasExpectedConfirmationDelay() {
        MealKit mealKit = recurringMealKitFactory.create(
            MEAL_KIT_ID,
            DELIVERY_DATE,
            PickupPointLocation.DESJARDINS,
            MealKitType.STANDARD
        );

        int actualConfirmationDelayInDays = mealKit.getConfirmationDelayInDays();
        assertEquals(MEAL_KIT_PREPARATION_DELAY_IN_DAYS, actualConfirmationDelayInDays);
    }
}

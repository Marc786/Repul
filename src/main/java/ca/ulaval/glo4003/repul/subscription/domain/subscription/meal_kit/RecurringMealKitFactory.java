package ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit;

import static ca.ulaval.glo4003.constant.Constants.Subscription.MEAL_KIT_PREPARATION_DELAY_IN_DAYS;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import java.time.LocalDate;

public class RecurringMealKitFactory implements MealKitFactory {

    @Override
    public MealKit create(
        MealKitId id,
        LocalDate deliveryDate,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType
    ) {
        return new MealKit(
            id,
            deliveryDate,
            MEAL_KIT_PREPARATION_DELAY_IN_DAYS,
            pickupPointLocation,
            mealKitType
        );
    }
}

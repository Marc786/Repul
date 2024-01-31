package ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import java.time.LocalDate;

public interface MealKitFactory {
    MealKit create(
        MealKitId id,
        LocalDate deliveryDate,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType
    );
}

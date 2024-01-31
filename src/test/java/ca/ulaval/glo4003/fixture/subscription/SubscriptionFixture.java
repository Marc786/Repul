package ca.ulaval.glo4003.fixture.subscription;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.RecurringMealKitFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.DeliverySchedule;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;

public class SubscriptionFixture {

    private final DeliveryScheduleFixture deliveryScheduleFixture =
        new DeliveryScheduleFixture();

    private final MealKitFactory mealKitFactory = new RecurringMealKitFactory();
    private SubscriptionId id = new SubscriptionId();
    private final PickupPointLocation pickupPointLocation = PickupPointLocation.CASAULT;
    private final MealKitType mealKitType = MealKitType.STANDARD;

    private final DeliverySchedule deliverySchedule = deliveryScheduleFixture.build();

    public Subscription build() {
        return new Subscription(
            id,
            deliverySchedule,
            pickupPointLocation,
            mealKitType,
            mealKitFactory
        );
    }

    public SubscriptionFixture withId() {
        this.id = new SubscriptionId();
        return this;
    }

    public SubscriptionFixture withId(SubscriptionId id) {
        this.id = id;
        return this;
    }
}

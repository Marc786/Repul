package ca.ulaval.glo4003.fixture.subscription;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.SubscriberConfirmationStatus;
import java.time.LocalDate;

public class MealKitFixture {

    public MealKitId mealKitId = new MealKitId();
    public final MealKitType mealKitType = MealKitType.STANDARD;
    public LocalDate deliveryDate = LocalDate.parse("2020-01-01");
    public final int confirmationDelayInDays = 2;
    public final PickupPointLocation pickupPointLocation = PickupPointLocation.DESJARDINS;
    public SubscriberConfirmationStatus subscriberConfirmationStatus =
        SubscriberConfirmationStatus.PENDING;

    public MealKit build() {
        return new MealKit(
            mealKitId,
            deliveryDate,
            confirmationDelayInDays,
            pickupPointLocation,
            mealKitType,
            subscriberConfirmationStatus
        );
    }

    public MealKitFixture withOtherId() {
        this.mealKitId = new MealKitId();
        return this;
    }

    public MealKitFixture withSubscriberConfirmationStatus(
        SubscriberConfirmationStatus subscriberConfirmationStatus
    ) {
        this.subscriberConfirmationStatus = subscriberConfirmationStatus;
        return this;
    }

    public MealKitFixture withDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }
}

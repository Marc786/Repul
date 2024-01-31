package ca.ulaval.glo4003.repul.subscription.domain.subscription;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.DeliverySchedule;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Subscription {

    private final SubscriptionId id;
    private final DeliverySchedule deliverySchedule;
    private final PickupPointLocation pickupPointLocation;
    private final MealKitType mealKitType;
    private final MealKitFactory mealKitFactory;
    private final List<MealKit> mealKits;

    public Subscription(
        SubscriptionId id,
        DeliverySchedule deliverySchedule,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType,
        MealKitFactory mealKitFactory
    ) {
        this.id = id;
        this.deliverySchedule = deliverySchedule;
        this.pickupPointLocation = pickupPointLocation;
        this.mealKitType = mealKitType;
        this.mealKitFactory = mealKitFactory;
        this.mealKits = new ArrayList<>();
    }

    public Subscription(
        SubscriptionId id,
        DeliverySchedule deliverySchedule,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType,
        MealKitFactory mealKitFactory,
        List<MealKit> mealKits
    ) {
        this.id = id;
        this.deliverySchedule = deliverySchedule;
        this.pickupPointLocation = pickupPointLocation;
        this.mealKitType = mealKitType;
        this.mealKitFactory = mealKitFactory;
        this.mealKits = mealKits;
    }

    public SubscriptionId getId() {
        return id;
    }

    public DeliverySchedule getDeliverySchedule() {
        return deliverySchedule;
    }

    public Frequency getFrequency() {
        return deliverySchedule.getFrequency();
    }

    public PickupPointLocation getPickupPointLocation() {
        return pickupPointLocation;
    }

    public MealKitType getMealKitType() {
        return mealKitType;
    }

    public LocalDate getStartDate() {
        return deliverySchedule.getStartDate();
    }

    public LocalDate getEndDate() {
        return deliverySchedule.getEndDate();
    }

    public MealKitFactory getMealKitFactory() {
        return mealKitFactory;
    }

    public MealKit getNextMealKit() {
        return mealKits.get(mealKits.size() - 1);
    }

    public void confirmNextMealKit() {
        getNextMealKit().confirm();
    }

    public void refuseNextMealKit() {
        getNextMealKit().refuse();
    }

    public void generateNextMealKit() {
        mealKits.add(
            mealKitFactory.create(
                new MealKitId(),
                deliverySchedule.getNextDeliveryDate(),
                pickupPointLocation,
                mealKitType
            )
        );
    }

    public List<MealKit> getMealKits() {
        return mealKits;
    }
}

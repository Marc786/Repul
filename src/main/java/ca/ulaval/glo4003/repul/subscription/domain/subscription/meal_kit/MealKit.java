package ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.date.DateUtils;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.exception.MealKitCancelledException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.exception.MealKitCannotBeConfirmedException;
import java.time.LocalDate;
import java.util.Objects;

public class MealKit {

    private final MealKitId id;
    private final MealKitType mealKitType;
    private final LocalDate deliveryDate;
    private final int confirmationDelayInDays;
    private final PickupPointLocation pickupPointLocation;
    private SubscriberConfirmationStatus subscriberConfirmationStatus;

    public MealKit(
        MealKitId id,
        LocalDate deliveryDate,
        int confirmationDelayInDays,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType
    ) {
        this.id = id;
        this.mealKitType = mealKitType;
        this.deliveryDate = deliveryDate;
        this.confirmationDelayInDays = confirmationDelayInDays;
        this.pickupPointLocation = pickupPointLocation;
        this.subscriberConfirmationStatus = SubscriberConfirmationStatus.PENDING;
    }

    public MealKit(
        MealKitId id,
        LocalDate deliveryDate,
        int confirmationDelayInDays,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType,
        SubscriberConfirmationStatus subscriberConfirmationStatus
    ) {
        this.id = id;
        this.mealKitType = mealKitType;
        this.deliveryDate = deliveryDate;
        this.confirmationDelayInDays = confirmationDelayInDays;
        this.pickupPointLocation = pickupPointLocation;
        this.subscriberConfirmationStatus = subscriberConfirmationStatus;
    }

    public MealKitId getId() {
        return id;
    }

    public MealKitType getMealKitType() {
        return mealKitType;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public int getConfirmationDelayInDays() {
        return confirmationDelayInDays;
    }

    public PickupPointLocation getPickupPointLocation() {
        return pickupPointLocation;
    }

    public SubscriberConfirmationStatus getSubscriberConfirmationStatus() {
        return subscriberConfirmationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MealKit mealKit = (MealKit) o;
        return Objects.equals(id, mealKit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void confirm() {
        canBeConfirmed();
        subscriberConfirmationStatus = SubscriberConfirmationStatus.PAID;
    }

    public void refuse() {
        subscriberConfirmationStatus = SubscriberConfirmationStatus.REFUSED;
    }

    private void canBeConfirmed() {
        if (DateUtils.isDateWithinRange(deliveryDate, confirmationDelayInDays)) {
            throw new MealKitCancelledException();
        } else if (
            !subscriberConfirmationStatus.equals(SubscriberConfirmationStatus.PENDING)
        ) {
            throw new MealKitCannotBeConfirmedException();
        }
    }
}

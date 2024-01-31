package ca.ulaval.glo4003.repul.subscription.infra.cloner;

import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.DeliverySchedule;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import com.rits.cloning.Cloner;
import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.util.Map;

class SubscriptionFastCloner implements IFastCloner {

    private final Cloner deepCloner = new Cloner();

    SubscriptionFastCloner() {
        deepCloner.registerFastCloner(
            DeliverySchedule.class,
            new DeliveryScheduleFastCloner()
        );
        deepCloner.registerFastCloner(MealKit.class, new MealKitFastCloner());
    }

    public Object clone(
        final Object t,
        final IDeepCloner cloner,
        final Map<Object, Object> clones
    ) {
        final Subscription original = (Subscription) t;

        return new Subscription(
            new SubscriptionId(original.getId().toString()),
            deepCloner.deepClone(original.getDeliverySchedule()),
            original.getPickupPointLocation(),
            original.getMealKitType(),
            original.getMealKitFactory(),
            deepCloner.deepClone(original.getMealKits())
        );
    }
}

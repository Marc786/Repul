package ca.ulaval.glo4003.repul.subscription.infra.cloner;

import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitId;
import com.rits.cloning.Cloner;
import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.time.LocalDate;
import java.util.Map;

class MealKitFastCloner implements IFastCloner {

    private final Cloner deepCloner = new Cloner();

    MealKitFastCloner() {
        deepCloner.registerFastCloner(LocalDate.class, new LocalDateFastCloner());
    }

    public Object clone(
        final Object t,
        final IDeepCloner cloner,
        final Map<Object, Object> clones
    ) {
        final MealKit original = (MealKit) t;

        return new MealKit(
            new MealKitId(original.getId().toString()),
            deepCloner.deepClone(original.getDeliveryDate()),
            original.getConfirmationDelayInDays(),
            original.getPickupPointLocation(),
            original.getMealKitType(),
            original.getSubscriberConfirmationStatus()
        );
    }
}

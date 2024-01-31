package ca.ulaval.glo4003.repul.subscription.infra.cloner;

import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.DeliverySchedule;
import com.rits.cloning.Cloner;
import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.time.LocalDate;
import java.util.Map;

class DeliveryScheduleFastCloner implements IFastCloner {

    private final Cloner deepCloner = new Cloner();

    DeliveryScheduleFastCloner() {
        deepCloner.registerFastCloner(LocalDate.class, new LocalDateFastCloner());
    }

    public Object clone(
        final Object t,
        final IDeepCloner cloner,
        final Map<Object, Object> clones
    ) {
        final DeliverySchedule original = (DeliverySchedule) t;

        return new DeliverySchedule(
            original.getDeliveryDayOfWeek(),
            deepCloner.deepClone(original.getStartDate()),
            deepCloner.deepClone(original.getEndDate()),
            original.getFrequency()
        );
    }
}

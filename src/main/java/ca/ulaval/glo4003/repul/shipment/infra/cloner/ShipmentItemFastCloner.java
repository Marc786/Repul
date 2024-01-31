package ca.ulaval.glo4003.repul.shipment.infra.cloner;

import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItem;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import ca.ulaval.glo4003.repul.subscription.infra.cloner.LocalDateFastCloner;
import com.rits.cloning.Cloner;
import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.time.LocalDate;
import java.util.Map;

public class ShipmentItemFastCloner implements IFastCloner {

    private final Cloner deepCloner = new Cloner();

    public ShipmentItemFastCloner() {
        deepCloner.registerFastCloner(LocalDate.class, new LocalDateFastCloner());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object clone(
        final Object t,
        final IDeepCloner cloner,
        final Map<Object, Object> clones
    ) {
        final ShipmentItem original = (ShipmentItem) t;

        return new ShipmentItem(
            new ShipmentItemId(original.getId().toString()),
            original.getDestination(),
            deepCloner.deepClone(original.getDeliveryDate()),
            original.getStatus()
        );
    }
}

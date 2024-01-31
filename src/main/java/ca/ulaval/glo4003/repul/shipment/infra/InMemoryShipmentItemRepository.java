package ca.ulaval.glo4003.repul.shipment.infra;

import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItem;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemRepository;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItems;
import ca.ulaval.glo4003.repul.shipment.domain.exception.ShipmentItemNotFoundException;
import ca.ulaval.glo4003.repul.shipment.infra.cloner.ShipmentItemFastCloner;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class InMemoryShipmentItemRepository implements ShipmentItemRepository {

    private final Cloner cloner = new Cloner();
    private final List<ShipmentItem> shipmentItems = new ArrayList<>();

    public InMemoryShipmentItemRepository() {
        cloner.registerFastCloner(ShipmentItem.class, new ShipmentItemFastCloner());
    }

    @Override
    public void save(ShipmentItem shipmentItem) {
        shipmentItems.removeIf(existingShipmentItem ->
            existingShipmentItem.getId().equals(shipmentItem.getId())
        );
        shipmentItems.add(shipmentItem);
    }

    @Override
    public ShipmentItem findById(ShipmentItemId shipmentItemId) {
        ShipmentItem shipmentItemFound = shipmentItems
            .stream()
            .filter(shipmentItem -> shipmentItem.getId().equals(shipmentItemId))
            .findFirst()
            .orElseThrow(() -> new ShipmentItemNotFoundException(shipmentItemId));
        return cloner.deepClone(shipmentItemFound);
    }

    @Override
    public ShipmentItems findAll() {
        ShipmentItems items = new ShipmentItems(shipmentItems);
        return cloner.deepClone(items);
    }
}

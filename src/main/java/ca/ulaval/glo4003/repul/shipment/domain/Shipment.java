package ca.ulaval.glo4003.repul.shipment.domain;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shipment {

    private final ShipmentId id;
    private final List<ShipmentItem> items;
    private final PickupPointLocation origin;

    public Shipment(ShipmentId id, List<ShipmentItem> items, PickupPointLocation origin) {
        this.items = items;
        this.origin = origin;
        this.id = id;
    }

    public void notifyShipmentCreated(
        CarrierCommunicationClient carrierCommunicationClient
    ) {
        String shipmentId = this.id.toString();
        String pickupPoint = origin.toString();
        Map<String, String> itemsAndDestinations = createItemsAndDestinationsMap();

        carrierCommunicationClient.sendEmailsToShipmentCarriers(
            shipmentId,
            pickupPoint,
            itemsAndDestinations
        );
    }

    public ShipmentId getId() {
        return id;
    }

    private Map<String, String> createItemsAndDestinationsMap() {
        Map<String, String> itemsAndDestinations = new HashMap<>();

        items.forEach(item ->
            itemsAndDestinations.put(
                item.getId().toString(),
                item.getDestination().toString()
            )
        );

        return itemsAndDestinations;
    }
}

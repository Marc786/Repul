package ca.ulaval.glo4003.fixture.shipment;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItem;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import java.time.LocalDate;

public class ShipmentItemFixture {

    private LocalDate deliveryDate = LocalDate.now();
    private ShipmentItemId id = new ShipmentItemId("shipmentId");
    private PickupPointLocation destination = PickupPointLocation.VACHON;

    public ShipmentItem build() {
        return new ShipmentItem(id, destination, deliveryDate);
    }

    public ShipmentItemFixture withId(ShipmentItemId id) {
        this.id = id;
        return this;
    }

    public ShipmentItemFixture withDestination(PickupPointLocation destination) {
        this.destination = destination;
        return this;
    }

    public ShipmentItemFixture withDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }
}

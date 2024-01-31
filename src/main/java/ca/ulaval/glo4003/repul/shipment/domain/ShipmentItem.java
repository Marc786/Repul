package ca.ulaval.glo4003.repul.shipment.domain;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import java.time.LocalDate;
import java.util.Objects;

public class ShipmentItem {

    private final LocalDate deliveryDate;
    private final ShipmentItemId id;
    private final PickupPointLocation destination;
    private ShipmentItemStatus status;

    public ShipmentItem(
        ShipmentItemId id,
        PickupPointLocation destination,
        LocalDate deliveryDate
    ) {
        this.id = id;
        this.destination = destination;
        this.deliveryDate = deliveryDate;
        this.status = ShipmentItemStatus.CONFIRMED;
    }

    public ShipmentItem(
        ShipmentItemId id,
        PickupPointLocation destination,
        LocalDate deliveryDate,
        ShipmentItemStatus status
    ) {
        this.id = id;
        this.destination = destination;
        this.deliveryDate = deliveryDate;
        this.status = status;
    }

    public ShipmentItemId getId() {
        return id;
    }

    public ShipmentItemStatus getStatus() {
        return status;
    }

    public PickupPointLocation getDestination() {
        return destination;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public boolean isAssembled() {
        return status == ShipmentItemStatus.ASSEMBLED;
    }

    public void setStatusAsAssembled() {
        this.status = ShipmentItemStatus.ASSEMBLED;
    }

    public void setStatusAsShipped() {
        this.status = ShipmentItemStatus.SHIPPED;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ShipmentItem shipmentItem = (ShipmentItem) obj;

        return id.equals(shipmentItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

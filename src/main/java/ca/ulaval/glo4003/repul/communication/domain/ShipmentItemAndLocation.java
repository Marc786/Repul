package ca.ulaval.glo4003.repul.communication.domain;

import java.util.Objects;

public class ShipmentItemAndLocation {

    private final String shipmentItemId;
    private final String shipmentItemLocation;

    public ShipmentItemAndLocation(String shipmentItemId, String shipmentItemLocation) {
        this.shipmentItemId = shipmentItemId;
        this.shipmentItemLocation = shipmentItemLocation;
    }

    public String shipmentItemId() {
        return shipmentItemId;
    }

    public String shipmentItemLocation() {
        return shipmentItemLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipmentItemAndLocation that = (ShipmentItemAndLocation) o;
        return (
            Objects.equals(shipmentItemId, that.shipmentItemId) &&
            Objects.equals(shipmentItemLocation, that.shipmentItemLocation)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipmentItemId, shipmentItemLocation);
    }
}

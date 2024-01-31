package ca.ulaval.glo4003.repul.shipment.domain;

import java.util.Objects;

public class ShipmentItemId {

    private final String id;

    public ShipmentItemId(String shipmentItemId) {
        this.id = shipmentItemId;
    }

    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShipmentItemId that = (ShipmentItemId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

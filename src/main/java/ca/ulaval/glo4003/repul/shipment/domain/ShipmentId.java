package ca.ulaval.glo4003.repul.shipment.domain;

import java.util.Objects;
import java.util.UUID;

public class ShipmentId {

    private final UUID value;

    public ShipmentId() {
        this.value = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShipmentId shipmentId = (ShipmentId) o;
        return value.equals(shipmentId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

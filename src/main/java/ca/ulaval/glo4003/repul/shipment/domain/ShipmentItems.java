package ca.ulaval.glo4003.repul.shipment.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ShipmentItems {

    private final List<ShipmentItem> items;

    public ShipmentItems(List<ShipmentItem> items) {
        this.items = items;
    }

    public List<ShipmentItem> getItems() {
        return items;
    }

    public ShipmentItems findByDeliveryDate(
        LocalDate lowerBoundDeliveryDate,
        LocalDate upperBoundDeliveryDate
    ) {
        return new ShipmentItems(
            items
                .stream()
                .filter(item ->
                    isWithinBounds(
                        item.getDeliveryDate(),
                        lowerBoundDeliveryDate,
                        upperBoundDeliveryDate
                    )
                )
                .toList()
        );
    }

    public ShipmentItems findAssembledShipmentItems() {
        return new ShipmentItems(
            items.stream().filter(ShipmentItem::isAssembled).toList()
        );
    }

    public void setStatusAsShipped() {
        items.forEach(ShipmentItem::setStatusAsShipped);
    }

    private boolean isWithinBounds(
        LocalDate date,
        LocalDate lowerBoundDate,
        LocalDate upperBoundDate
    ) {
        return (
            date.isEqual(lowerBoundDate) ||
            date.isEqual(upperBoundDate) ||
            (date.isAfter(lowerBoundDate) && date.isBefore(upperBoundDate))
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ShipmentItems shipmentItems = (ShipmentItems) obj;

        return items.equals(shipmentItems.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}

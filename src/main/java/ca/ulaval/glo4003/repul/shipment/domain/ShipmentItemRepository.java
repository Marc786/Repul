package ca.ulaval.glo4003.repul.shipment.domain;

public interface ShipmentItemRepository {
    void save(ShipmentItem shipmentItem);

    ShipmentItem findById(ShipmentItemId shipmentItemId);

    ShipmentItems findAll();
}

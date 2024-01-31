package ca.ulaval.glo4003.repul.communication.domain;

import java.util.List;

public class ShipmentInfo {

    private final String shipmentId;
    private final String pickUpPointLocation;
    private final List<ShipmentItemAndLocation> shipmentItemsIdAndLocation;

    public ShipmentInfo(
        String shipmentId,
        String pickUpPointLocation,
        List<ShipmentItemAndLocation> shipmentItemsIdAndLocation
    ) {
        this.shipmentId = shipmentId;
        this.pickUpPointLocation = pickUpPointLocation;
        this.shipmentItemsIdAndLocation = shipmentItemsIdAndLocation;
    }

    public String shipmentId() {
        return shipmentId;
    }

    public String pickUpPointLocation() {
        return pickUpPointLocation;
    }

    public List<ShipmentItemAndLocation> shipmentItemsIdAndLocation() {
        return shipmentItemsIdAndLocation;
    }
}

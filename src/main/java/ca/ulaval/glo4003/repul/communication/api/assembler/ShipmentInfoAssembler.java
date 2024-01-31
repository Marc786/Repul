package ca.ulaval.glo4003.repul.communication.api.assembler;

import ca.ulaval.glo4003.repul.communication.domain.ShipmentItemAndLocation;
import java.util.List;
import java.util.Map;

public class ShipmentInfoAssembler {

    public List<ShipmentItemAndLocation> toShipmentItemsAndLocation(
        Map<String, String> shipmentItemsAndLocation
    ) {
        return shipmentItemsAndLocation
            .entrySet()
            .stream()
            .map(item -> new ShipmentItemAndLocation(item.getKey(), item.getValue()))
            .toList();
    }
}

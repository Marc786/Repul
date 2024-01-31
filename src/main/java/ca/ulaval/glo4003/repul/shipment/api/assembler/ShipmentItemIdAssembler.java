package ca.ulaval.glo4003.repul.shipment.api.assembler;

import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemRequest;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import java.util.List;

public class ShipmentItemIdAssembler {

    public List<ShipmentItemId> toShipmentItemIds(
        List<AssembledShipmentItemRequest> assembledShipmentItemIds
    ) {
        return assembledShipmentItemIds.stream().map(this::toShipmentItemId).toList();
    }

    public ShipmentItemId toShipmentItemId(
        AssembledShipmentItemRequest assembledShipmentItemId
    ) {
        return new ShipmentItemId(assembledShipmentItemId.shipmentItemId());
    }
}

package ca.ulaval.glo4003.repul.kitchen.infra.shipment;

import ca.ulaval.glo4003.repul.kitchen.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.infra.shipment.assembler.AssembledShipmentItemsRequestAssembler;
import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemsRequest;
import java.util.List;

public class InternalShipmentClient implements ShipmentClient {

    private final ShipmentResource shipmentResource;
    private final AssembledShipmentItemsRequestAssembler assembledShipmentItemsRequestAssembler =
        new AssembledShipmentItemsRequestAssembler();

    public InternalShipmentClient(ShipmentResource shipmentResource) {
        this.shipmentResource = shipmentResource;
    }

    @Override
    public void addAssembledShipmentItem(List<MealId> mealId) {
        AssembledShipmentItemsRequest assembledShipmentItemsRequest =
            assembledShipmentItemsRequestAssembler.toRequest(mealId);
        shipmentResource.assembleShipmentItems(assembledShipmentItemsRequest);
    }
}

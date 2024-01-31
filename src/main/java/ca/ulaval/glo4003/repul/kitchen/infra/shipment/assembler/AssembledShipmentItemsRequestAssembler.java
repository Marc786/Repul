package ca.ulaval.glo4003.repul.kitchen.infra.shipment.assembler;

import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemRequest;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemsRequest;
import java.util.List;

public class AssembledShipmentItemsRequestAssembler {

    public AssembledShipmentItemsRequest toRequest(List<MealId> mealIds) {
        return new AssembledShipmentItemsRequest(
            mealIds.stream().map(this::toRequest).toList()
        );
    }

    private AssembledShipmentItemRequest toRequest(MealId mealId) {
        return new AssembledShipmentItemRequest(mealId.toString());
    }
}

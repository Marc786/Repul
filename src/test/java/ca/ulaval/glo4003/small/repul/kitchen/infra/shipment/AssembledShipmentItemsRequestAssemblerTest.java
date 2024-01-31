package ca.ulaval.glo4003.small.repul.kitchen.infra.shipment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.infra.shipment.assembler.AssembledShipmentItemsRequestAssembler;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemRequest;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemsRequest;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssembledShipmentItemsRequestAssemblerTest {

    private static final String MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final MealId MEAL_ID = new MealId(MEAL_ID_STRING);
    private static final List<MealId> MEAL_IDS = List.of(MEAL_ID);
    private static final AssembledShipmentItemRequest ASSEMBLED_SHIPMENT_ITEM_REQUEST =
        new AssembledShipmentItemRequest(MEAL_ID_STRING);
    private static final AssembledShipmentItemsRequest ASSEMBLED_SHIPMENT_ITEMS_REQUEST =
        new AssembledShipmentItemsRequest(List.of(ASSEMBLED_SHIPMENT_ITEM_REQUEST));
    private AssembledShipmentItemsRequestAssembler assembler;

    @BeforeEach
    void setup() {
        assembler = new AssembledShipmentItemsRequestAssembler();
    }

    @Test
    void toRequest_returnsAssembledShipmentItemsRequest() {
        AssembledShipmentItemsRequest ActualRequest = assembler.toRequest(MEAL_IDS);

        assertEquals(ASSEMBLED_SHIPMENT_ITEMS_REQUEST, ActualRequest);
    }
}

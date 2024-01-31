package ca.ulaval.glo4003.small.repul.shipment.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.repul.shipment.api.assembler.ShipmentItemIdAssembler;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemRequest;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import java.util.List;
import org.junit.jupiter.api.Test;

class ShipmentItemIdAssemblerTest {

    private static final String SHIPMENT_ITEM_ID_1 = "shipmentItemId1";
    private static final AssembledShipmentItemRequest SHIPMENT_ITEM_ID_REQUEST_1 =
        new AssembledShipmentItemRequest(SHIPMENT_ITEM_ID_1);
    private static final String SHIPMENT_ITEM_ID_2 = "shipmentItemId2";
    private static final AssembledShipmentItemRequest SHIPMENT_ITEM_ID_REQUEST_2 =
        new AssembledShipmentItemRequest(SHIPMENT_ITEM_ID_2);
    private final ShipmentItemIdAssembler shipmentItemIdAssembler =
        new ShipmentItemIdAssembler();

    @Test
    void assembledShipmentItemIds_toShipmentItemIds_returnsShipmentItemIds() {
        List<AssembledShipmentItemRequest> assembledShipmentItemIds = List.of(
            SHIPMENT_ITEM_ID_REQUEST_1,
            SHIPMENT_ITEM_ID_REQUEST_2
        );
        List<ShipmentItemId> expectedShipmentItemIds = List.of(
            new ShipmentItemId(SHIPMENT_ITEM_ID_1),
            new ShipmentItemId(SHIPMENT_ITEM_ID_2)
        );

        List<ShipmentItemId> actualShipmentItemIds =
            shipmentItemIdAssembler.toShipmentItemIds(assembledShipmentItemIds);

        assertEquals(expectedShipmentItemIds, actualShipmentItemIds);
    }
}

package ca.ulaval.glo4003.small.repul.communication.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.repul.communication.api.assembler.ShipmentInfoAssembler;
import ca.ulaval.glo4003.repul.communication.domain.ShipmentItemAndLocation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipmentInfoAssemblerTest {

    private static final String A_SHIPMENT_ITEM_ID = "FIRST_ID";
    private static final String A_SHIPMENT_ITEM_LOCATION = "VACHON";

    private static final String ANOTHER_SHIPMENT_ITEM_ID = "SECOND_ID";
    private static final String ANOTHER_SHIPMENT_ITEM_LOCATION = "DKN";

    private static final ShipmentItemAndLocation aShipmentItemAndLocation =
        new ShipmentItemAndLocation(A_SHIPMENT_ITEM_ID, A_SHIPMENT_ITEM_LOCATION);
    private static final ShipmentItemAndLocation anotherShipmentItemAndLocation =
        new ShipmentItemAndLocation(
            ANOTHER_SHIPMENT_ITEM_ID,
            ANOTHER_SHIPMENT_ITEM_LOCATION
        );

    private ShipmentInfoAssembler shipmentInfoAssembler;

    @BeforeEach
    void setup() {
        shipmentInfoAssembler = new ShipmentInfoAssembler();
    }

    @Test
    void emptyMap_toShipmentItemsAndLocation_returnsEmptyList() {
        List<ShipmentItemAndLocation> shipmentItemAndLocation =
            shipmentInfoAssembler.toShipmentItemsAndLocation(Map.of());

        assertTrue(shipmentItemAndLocation.isEmpty());
    }

    @Test
    void twoShipmentItemAndLocationsInMap_toShipmentItemsAndLocation_returnsListOfShipmentItemAndLocation() {
        Map<String, String> shipments = Map.of(
            A_SHIPMENT_ITEM_ID,
            A_SHIPMENT_ITEM_LOCATION,
            ANOTHER_SHIPMENT_ITEM_ID,
            ANOTHER_SHIPMENT_ITEM_LOCATION
        );
        List<ShipmentItemAndLocation> expectedShipmentItemAndLocations = List.of(
            aShipmentItemAndLocation,
            anotherShipmentItemAndLocation
        );

        List<ShipmentItemAndLocation> actualShipmentItemAndLocations =
            shipmentInfoAssembler.toShipmentItemsAndLocation(shipments);

        assertTrue(
            expectedShipmentItemAndLocations.containsAll(actualShipmentItemAndLocations)
        );
        assertTrue(
            actualShipmentItemAndLocations.containsAll(expectedShipmentItemAndLocations)
        );
        assertEquals(
            actualShipmentItemAndLocations.size(),
            expectedShipmentItemAndLocations.size()
        );
    }
}

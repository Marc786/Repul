package ca.ulaval.glo4003.small.repul.shipment.domain;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItem;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ShipmentItemTest {

    private static final ShipmentItemId SHIPMENT_ITEM_ID = new ShipmentItemId("id");
    private static final PickupPointLocation PICKUP_POINT_LOCATION =
        PickupPointLocation.VACHON;
    private static final LocalDate DELIVERY_DATE = LocalDate.of(2023, 1, 1);

    @Test
    void createShipmentItem_statusIsConfirmed() {
        ShipmentItemStatus expectedStatus = ShipmentItemStatus.CONFIRMED;

        ShipmentItem shipmentItem = new ShipmentItem(
            SHIPMENT_ITEM_ID,
            PICKUP_POINT_LOCATION,
            DELIVERY_DATE
        );

        ShipmentItemStatus actualStatus = shipmentItem.getStatus();
        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    void shipmentItemIsAssembled_isAssembled_returnsTrue() {
        ShipmentItem shipmentItem = new ShipmentItem(
            SHIPMENT_ITEM_ID,
            PICKUP_POINT_LOCATION,
            DELIVERY_DATE
        );

        shipmentItem.setStatusAsAssembled();

        assertTrue(shipmentItem.isAssembled());
    }

    @Test
    void shipmentItemIsNotAssembled_isAssembled_returnsFalse() {
        ShipmentItem shipmentItem = new ShipmentItem(
            SHIPMENT_ITEM_ID,
            PICKUP_POINT_LOCATION,
            DELIVERY_DATE
        );

        assertFalse(shipmentItem.isAssembled());
    }
}

package ca.ulaval.glo4003.small.repul.shipment.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.fixture.shipment.ShipmentItemFixture;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItem;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemStatus;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItems;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipmentItemsTest {

    private static final ShipmentItemId SHIPMENT_ITEM_ID = new ShipmentItemId(
        "shipmentItemId"
    );
    private static final ShipmentItemId OTHER_SHIPMENT_ITEM_ID = new ShipmentItemId(
        "otherShipmentItemId"
    );
    private static final PickupPointLocation PICKUP_POINT_LOCATION =
        PickupPointLocation.VACHON;

    public static final LocalDate EARLIEST_DATE = LocalDate.of(2023, 1, 1);
    public static final LocalDate MIDDLE_DATE = LocalDate.of(2023, 6, 15);
    public static final LocalDate LATEST_DATE = LocalDate.of(2023, 12, 31);
    private static final LocalDate DELIVERY_DATE = MIDDLE_DATE;
    private final ShipmentItemFixture shipmentItemFixture = new ShipmentItemFixture();
    private ShipmentItem shipmentItem;
    private ShipmentItems shipmentItems;

    @BeforeEach
    void setup() {
        shipmentItem =
            shipmentItemFixture
                .withId(SHIPMENT_ITEM_ID)
                .withDestination(PICKUP_POINT_LOCATION)
                .withDeliveryDate(DELIVERY_DATE)
                .build();

        shipmentItems = new ShipmentItems(List.of(shipmentItem));
    }

    @Test
    void shipmentItemBetweenDates_findByDeliveryDate_returnsValidShipmentItems() {
        ShipmentItems foundShipmentItems = shipmentItems.findByDeliveryDate(
            EARLIEST_DATE,
            LATEST_DATE
        );

        assertTrue(foundShipmentItems.getItems().contains(shipmentItem));
    }

    @Test
    void shipmentItemAtLowerBoundDate_findByDeliveryDate_returnsValidShipmentItems() {
        ShipmentItems foundShipmentItems = shipmentItems.findByDeliveryDate(
            MIDDLE_DATE,
            LATEST_DATE
        );

        assertTrue(foundShipmentItems.getItems().contains(shipmentItem));
    }

    @Test
    void shipmentItemAtUpperBoundDate_findByDeliveryDate_returnsValidShipmentItems() {
        ShipmentItems foundShipmentItems = shipmentItems.findByDeliveryDate(
            EARLIEST_DATE,
            MIDDLE_DATE
        );

        assertTrue(foundShipmentItems.getItems().contains(shipmentItem));
    }

    @Test
    void shipmentItemAfterDates_findByDeliveryDate_returnsEmptyShipmentItems() {
        ShipmentItems foundShipmentItems = shipmentItems.findByDeliveryDate(
            EARLIEST_DATE,
            EARLIEST_DATE
        );

        assertTrue(foundShipmentItems.getItems().isEmpty());
    }

    @Test
    void shipmentItemBeforeDates_findByDeliveryDate_returnsEmptyShipmentItems() {
        ShipmentItems foundShipmentItems = shipmentItems.findByDeliveryDate(
            LATEST_DATE,
            LATEST_DATE
        );

        assertTrue(foundShipmentItems.getItems().isEmpty());
    }

    @Test
    void noAssembledShipmentItems_findAssembledShipmentItems_returnsEmptyShipmentItems() {
        ShipmentItems foundShipmentItems = shipmentItems.findAssembledShipmentItems();

        assertTrue(foundShipmentItems.getItems().isEmpty());
    }

    @Test
    void assembledShipmentItems_findAssembledShipmentItems_returnsValidShipmentItems() {
        shipmentItem.setStatusAsAssembled();

        ShipmentItems foundShipmentItems = shipmentItems.findAssembledShipmentItems();

        assertTrue(foundShipmentItems.getItems().contains(shipmentItem));
    }

    @Test
    void multipleShipmentItems_setStatusAsShipped_setsAllStatusAsShipped() {
        ShipmentItem otherShipmentItem = new ShipmentItem(
            OTHER_SHIPMENT_ITEM_ID,
            PICKUP_POINT_LOCATION,
            DELIVERY_DATE
        );
        shipmentItems = new ShipmentItems(List.of(shipmentItem, otherShipmentItem));
        ShipmentItemStatus expectedStatus = ShipmentItemStatus.SHIPPED;

        shipmentItems.setStatusAsShipped();

        ShipmentItemStatus actualShipmentStatus = shipmentItem.getStatus();
        ShipmentItemStatus actualOtherShipmentStatus = otherShipmentItem.getStatus();
        assertEquals(expectedStatus, actualShipmentStatus);
        assertEquals(expectedStatus, actualOtherShipmentStatus);
    }
}

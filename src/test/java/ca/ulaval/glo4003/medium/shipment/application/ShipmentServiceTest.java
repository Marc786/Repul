package ca.ulaval.glo4003.medium.shipment.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.application.ShipmentService;
import ca.ulaval.glo4003.repul.shipment.domain.*;
import ca.ulaval.glo4003.repul.shipment.domain.exception.ShipmentItemNotFoundException;
import ca.ulaval.glo4003.repul.shipment.infra.InMemoryShipmentItemRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipmentServiceTest {

    private static final ShipmentItemId SHIPMENT_ITEM_ID = new ShipmentItemId("foo");
    private static final ShipmentItemId OTHER_SHIPMENT_ITEM_ID = new ShipmentItemId(
        "bar"
    );
    private static final LocalDate DELIVERY_DATE = LocalDate.parse("2024-10-10");
    private static final LocalDate BEFORE_DELIVERY_DATE = LocalDate.parse("2024-10-09");
    private static final LocalDate AFTER_DELIVERY_DATE = LocalDate.parse("2024-10-11");
    private final CarrierCommunicationClient carrierCommunicationClient = mock(
        CarrierCommunicationClient.class
    );
    private ShipmentService shipmentService;
    private ShipmentItemRepository shipmentRepository;

    @BeforeEach
    void setup() {
        shipmentRepository = new InMemoryShipmentItemRepository();
        shipmentService =
            new ShipmentService(shipmentRepository, carrierCommunicationClient);
    }

    @Test
    void addConfirmedShipmentItem_itemIsAddedToRepository() {
        shipmentService.addConfirmedShipmentItem(
            SHIPMENT_ITEM_ID,
            PickupPointLocation.VACHON,
            DELIVERY_DATE
        );

        ShipmentItem shipmentItem = shipmentRepository.findById(SHIPMENT_ITEM_ID);
        assertEquals(SHIPMENT_ITEM_ID, shipmentItem.getId());
        assertEquals(PickupPointLocation.VACHON, shipmentItem.getDestination());
        assertEquals(DELIVERY_DATE, shipmentItem.getDeliveryDate());
    }

    @Test
    void addAssembledShipmentItems_itemsAreAddedToRepository() {
        shipmentService.addConfirmedShipmentItem(
            SHIPMENT_ITEM_ID,
            PickupPointLocation.VACHON,
            DELIVERY_DATE
        );
        shipmentService.addConfirmedShipmentItem(
            OTHER_SHIPMENT_ITEM_ID,
            PickupPointLocation.VACHON,
            DELIVERY_DATE
        );
        int expectedNumberOfAssembledShipmentItems = 2;

        shipmentService.addAssembledShipmentItems(
            List.of(SHIPMENT_ITEM_ID, OTHER_SHIPMENT_ITEM_ID)
        );

        assertEquals(
            expectedNumberOfAssembledShipmentItems,
            shipmentRepository.findAll().findAssembledShipmentItems().getItems().size()
        );
    }

    @Test
    void shipmentItemNotInRepository_addAssembledShipmentItems_throwsItemNotFoundException() {
        assertThrows(
            ShipmentItemNotFoundException.class,
            () -> shipmentService.addAssembledShipmentItems(List.of(SHIPMENT_ITEM_ID))
        );
    }

    @Test
    void createShipment_shipmentIsCreated() {
        shipmentService.addConfirmedShipmentItem(
            SHIPMENT_ITEM_ID,
            PickupPointLocation.VACHON,
            DELIVERY_DATE
        );
        shipmentService.addAssembledShipmentItems(List.of(SHIPMENT_ITEM_ID));

        ShipmentId shipmentId = shipmentService.createShipment(
            BEFORE_DELIVERY_DATE,
            AFTER_DELIVERY_DATE
        );

        assertNotNull(shipmentId);
    }

    @Test
    void createShipment_shipmentItemIsShipped() {
        shipmentService.addConfirmedShipmentItem(
            SHIPMENT_ITEM_ID,
            PickupPointLocation.VACHON,
            DELIVERY_DATE
        );
        shipmentService.addAssembledShipmentItems(List.of(SHIPMENT_ITEM_ID));

        shipmentService.createShipment(BEFORE_DELIVERY_DATE, AFTER_DELIVERY_DATE);

        assertEquals(
            ShipmentItemStatus.SHIPPED,
            shipmentRepository.findById(SHIPMENT_ITEM_ID).getStatus()
        );
    }
}

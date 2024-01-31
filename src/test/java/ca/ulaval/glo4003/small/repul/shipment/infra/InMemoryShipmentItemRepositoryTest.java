package ca.ulaval.glo4003.small.repul.shipment.infra;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.fixture.shipment.ShipmentItemFixture;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItem;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemRepository;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItems;
import ca.ulaval.glo4003.repul.shipment.domain.exception.ShipmentItemNotFoundException;
import ca.ulaval.glo4003.repul.shipment.infra.InMemoryShipmentItemRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryShipmentItemRepositoryTest {

    private final ShipmentItemFixture shipmentItemFixture = new ShipmentItemFixture();
    private ShipmentItem shipmentItem;
    private ShipmentItem anotherShipmentItem;
    private ShipmentItemRepository shipmentItemRepository;

    @BeforeEach
    void setup() {
        shipmentItem = shipmentItemFixture.build();
        anotherShipmentItem =
            shipmentItemFixture
                .withId(new ShipmentItemId("anotherOneBitesTheDust"))
                .build();

        shipmentItemRepository = new InMemoryShipmentItemRepository();
    }

    @Test
    void savedShipmentItems_findAll_shipmentItemsAreReturned() {
        shipmentItemRepository.save(shipmentItem);
        shipmentItemRepository.save(anotherShipmentItem);
        ShipmentItems expectedShipmentItem = new ShipmentItems(
            List.of(shipmentItem, anotherShipmentItem)
        );

        ShipmentItems actualShipmentItems = shipmentItemRepository.findAll();

        assertNotSame(expectedShipmentItem, actualShipmentItems);
        assertEquals(expectedShipmentItem, actualShipmentItems);
    }

    @Test
    void savedShipmentItems_findById_shipmentItemIsReturned() {
        shipmentItemRepository.save(shipmentItem);
        shipmentItemRepository.save(anotherShipmentItem);
        ShipmentItem expectedShipmentItem = shipmentItem;

        ShipmentItem actualShipmentItem = shipmentItemRepository.findById(
            shipmentItem.getId()
        );

        assertNotSame(expectedShipmentItem, actualShipmentItem);
        assertEquals(expectedShipmentItem, actualShipmentItem);
    }

    @Test
    void newShipmentItem_findById_shipmentItemNotFoundExceptionIsThrown() {
        assertThrows(
            ShipmentItemNotFoundException.class,
            () -> shipmentItemRepository.findById(shipmentItem.getId())
        );
    }
}

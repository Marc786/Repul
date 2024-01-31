package ca.ulaval.glo4003.small.repul.shipment.application;

import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.fixture.shipment.ShipmentItemFixture;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.application.ShipmentService;
import ca.ulaval.glo4003.repul.shipment.domain.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

class ShipmentServiceTest {

    private static final LocalDate LOWER_BOUND_DATE = LocalDate.of(2023, 1, 1);
    private static final LocalDate UPPER_BOUND_DATE = LocalDate.of(2023, 1, 2);
    private static final PickupPointLocation PICKUP_POINT_LOCATION =
        PickupPointLocation.DESJARDINS;
    private static final ShipmentItemId SHIPMENT_ITEM_ID = new ShipmentItemId(
        "shipmentItemId"
    );

    private ShipmentService shipmentService;
    private final ShipmentItemFixture shipmentItemFixture = new ShipmentItemFixture();
    private ShipmentItem shipmentItem;
    private ShipmentItems shipmentItems;
    private final ShipmentItemRepository shipmentItemRepository = mock(
        ShipmentItemRepository.class
    );
    private final CarrierCommunicationClient communicationClient = mock(
        CarrierCommunicationClient.class
    );

    @BeforeEach
    void setup() {
        shipmentService =
            new ShipmentService(shipmentItemRepository, communicationClient);

        shipmentItem =
            shipmentItemFixture
                .withId(SHIPMENT_ITEM_ID)
                .withDestination(PICKUP_POINT_LOCATION)
                .withDeliveryDate(LOWER_BOUND_DATE)
                .build();

        shipmentItems = new ShipmentItems(List.of(shipmentItem));
    }

    @Test
    void createShipment_notifiesShipmentCreated() {
        when(shipmentItemRepository.findAll()).thenReturn(shipmentItems);
        when(shipmentItemRepository.findById(shipmentItem.getId()))
            .thenReturn(shipmentItem);
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put(
            shipmentItem.getId().toString(),
            shipmentItem.getDestination().toString()
        );
        shipmentService.addAssembledShipmentItems(List.of(shipmentItem.getId()));

        shipmentService.createShipment(LOWER_BOUND_DATE, UPPER_BOUND_DATE);

        verify(communicationClient)
            .sendEmailsToShipmentCarriers(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(shipmentItem.getDestination().toString()),
                ArgumentMatchers.eq(expectedMap)
            );
    }
}

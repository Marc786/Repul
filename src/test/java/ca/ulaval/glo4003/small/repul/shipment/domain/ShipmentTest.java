package ca.ulaval.glo4003.small.repul.shipment.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.domain.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipmentTest {

    private static final ShipmentId SHIPMENT_ID = new ShipmentId();
    private static final String SHIPMENT_ID_STRING = SHIPMENT_ID.toString();
    private static final ShipmentItemId SHIPMENT_ITEM_ID = new ShipmentItemId(
        "shipmentItemId"
    );
    private static final String SHIPMENT_ITEM_ID_STRING = SHIPMENT_ITEM_ID.toString();
    private static final ShipmentItem SHIPMENT_ITEM = new ShipmentItem(
        SHIPMENT_ITEM_ID,
        PickupPointLocation.VACHON,
        LocalDate.of(2025, 1, 1)
    );
    private static final PickupPointLocation PICKUP_POINT_LOCATION =
        PickupPointLocation.VACHON;
    private static final String PICKUP_POINT_LOCATION_STRING =
        PICKUP_POINT_LOCATION.toString();
    private Map<String, String> itemsAndDestination;

    private final CarrierCommunicationClient communicationClient = mock(
        CarrierCommunicationClient.class
    );

    @BeforeEach
    void setup() {
        itemsAndDestination = new HashMap<>();
        itemsAndDestination.put(SHIPMENT_ITEM_ID_STRING, PICKUP_POINT_LOCATION_STRING);
    }

    @Test
    void notifyShipmentCreated_sendEmailsToShipmentCarriersIsCalled() {
        Shipment shipment = new Shipment(
            SHIPMENT_ID,
            List.of(SHIPMENT_ITEM),
            PICKUP_POINT_LOCATION
        );

        shipment.notifyShipmentCreated(communicationClient);

        verify(communicationClient)
            .sendEmailsToShipmentCarriers(
                SHIPMENT_ID_STRING,
                PICKUP_POINT_LOCATION_STRING,
                itemsAndDestination
            );
    }
}

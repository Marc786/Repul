package ca.ulaval.glo4003.small.repul.shipment.infra;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.communication.api.CommunicationResource;
import ca.ulaval.glo4003.repul.communication.api.dto.request.ShipmentInfoRequest;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import ca.ulaval.glo4003.repul.shipment.infra.InternalCarrierCommunicationClient;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class InternalCarrierCommunicationClientTest {

    private static final String SHIPMENT_ID = UUID.randomUUID().toString();
    private static final PickupPointLocation SHIPMENT_PICKUP_POINT_LOCATION =
        PickupPointLocation.DESJARDINS;
    private static final PickupPointLocation PICKUP_POINT_LOCATION =
        PickupPointLocation.VACHON;
    private static final ShipmentItemId SHIPMENT_ITEM_ID = new ShipmentItemId(
        UUID.randomUUID().toString()
    );
    private static final Map<String, String> SHIPMENT_ITEMS_AND_LOCATION = Map.of(
        SHIPMENT_ITEM_ID.toString(),
        PICKUP_POINT_LOCATION.toString()
    );

    private final CommunicationResource communicationResourceMock = mock(
        CommunicationResource.class
    );
    private final InternalCarrierCommunicationClient internalCarrierCommunicationClient =
        new InternalCarrierCommunicationClient(communicationResourceMock);

    @Test
    void shipmentEmailInfo_sendEmailsToShipmentCarriers_sendEmailsToCarrierIsCalled() {
        ShipmentInfoRequest shipmentInfoRequest = new ShipmentInfoRequest(
            SHIPMENT_ID,
            SHIPMENT_PICKUP_POINT_LOCATION.toString(),
            SHIPMENT_ITEMS_AND_LOCATION
        );

        internalCarrierCommunicationClient.sendEmailsToShipmentCarriers(
            SHIPMENT_ID,
            SHIPMENT_PICKUP_POINT_LOCATION.toString(),
            SHIPMENT_ITEMS_AND_LOCATION
        );

        verify(communicationResourceMock)
            .sendEmailsToShipmentCarriers(shipmentInfoRequest);
    }
}

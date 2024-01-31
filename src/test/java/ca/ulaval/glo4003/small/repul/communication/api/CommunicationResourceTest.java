package ca.ulaval.glo4003.small.repul.communication.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.repul.communication.api.CommunicationResource;
import ca.ulaval.glo4003.repul.communication.api.dto.request.ShipmentInfoRequest;
import ca.ulaval.glo4003.repul.communication.application.CarrierCommunicationService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommunicationResourceTest {

    private static final String SHIPMENT_INFO_REQUEST_ID = "ID";
    private static final String SHIPMENT_INFO_REQUEST_PICKUP_POINT_LOCATION = "DNK";
    private static final Map<
        String,
        String
    > SHIPMENT_INFO_REQUEST_SHIPMENT_ITEMS_AND_LOCATION = Map.of("AN_ID", "PLT");

    private final ShipmentInfoRequest shipmentInfoRequest = new ShipmentInfoRequest(
        SHIPMENT_INFO_REQUEST_ID,
        SHIPMENT_INFO_REQUEST_PICKUP_POINT_LOCATION,
        SHIPMENT_INFO_REQUEST_SHIPMENT_ITEMS_AND_LOCATION
    );

    private final CarrierCommunicationService carrierCommunicationServiceMock = mock(
        CarrierCommunicationService.class
    );
    private CommunicationResource communicationResource;

    @BeforeEach
    void setup() {
        communicationResource =
            new CommunicationResource(carrierCommunicationServiceMock);
    }

    @Test
    void sendEmailsToShipmentCarriers_callSendEmailToShipmentCarriers() {
        communicationResource.sendEmailsToShipmentCarriers(shipmentInfoRequest);

        verify(carrierCommunicationServiceMock).sendEmailToShipmentCarriers(any());
    }
}

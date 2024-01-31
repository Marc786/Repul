package ca.ulaval.glo4003.repul.shipment.infra;

import ca.ulaval.glo4003.repul.communication.api.CommunicationResource;
import ca.ulaval.glo4003.repul.communication.api.dto.request.ShipmentInfoRequest;
import ca.ulaval.glo4003.repul.shipment.domain.CarrierCommunicationClient;
import java.util.Map;

public class InternalCarrierCommunicationClient implements CarrierCommunicationClient {

    private final CommunicationResource communicationResource;

    public InternalCarrierCommunicationClient(
        CommunicationResource communicationResource
    ) {
        this.communicationResource = communicationResource;
    }

    @Override
    public void sendEmailsToShipmentCarriers(
        String shipmentId,
        String pickUpPointLocation,
        Map<String, String> shipmentItemsAndLocation
    ) {
        ShipmentInfoRequest shipmentInfoRequest = new ShipmentInfoRequest(
            shipmentId,
            pickUpPointLocation,
            shipmentItemsAndLocation
        );
        communicationResource.sendEmailsToShipmentCarriers(shipmentInfoRequest);
    }
}

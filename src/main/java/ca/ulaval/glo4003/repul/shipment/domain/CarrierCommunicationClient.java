package ca.ulaval.glo4003.repul.shipment.domain;

import java.util.Map;

public interface CarrierCommunicationClient {
    void sendEmailsToShipmentCarriers(
        String shipmentId,
        String pickUpPointLocation,
        Map<String, String> shipmentItemsAndLocation
    );
}

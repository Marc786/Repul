package ca.ulaval.glo4003.repul.communication.application;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.communication.domain.CarrierCommunicationClient;
import ca.ulaval.glo4003.repul.communication.domain.CarrierProfileClient;
import ca.ulaval.glo4003.repul.communication.domain.ShipmentInfo;
import java.util.List;

public class CarrierCommunicationService {

    private final CarrierCommunicationClient carrierCommunicationClient;
    private final CarrierProfileClient carrierProfileClient;

    public CarrierCommunicationService(
        CarrierCommunicationClient carrierCommunicationClient,
        CarrierProfileClient carrierProfileClient
    ) {
        this.carrierCommunicationClient = carrierCommunicationClient;
        this.carrierProfileClient = carrierProfileClient;
    }

    public void sendEmailToShipmentCarriers(ShipmentInfo shipmentInfo) {
        List<EmailAddress> carriersEmails = carrierProfileClient.getCarriersEmail();
        carrierCommunicationClient.notifyCarriersShipmentCreated(
            shipmentInfo,
            carriersEmails
        );
    }
}

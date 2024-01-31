package ca.ulaval.glo4003.repul.communication.api.config;

import ca.ulaval.glo4003.repul.account.api.carrier.CarrierProfileResource;
import ca.ulaval.glo4003.repul.account.api.config.CarrierProfileResourceFactory;
import ca.ulaval.glo4003.repul.communication.api.CommunicationResource;
import ca.ulaval.glo4003.repul.communication.application.CarrierCommunicationService;
import ca.ulaval.glo4003.repul.communication.domain.CarrierCommunicationClient;
import ca.ulaval.glo4003.repul.communication.domain.CarrierProfileClient;
import ca.ulaval.glo4003.repul.communication.infra.CarrierMailClient;
import ca.ulaval.glo4003.repul.communication.infra.InternalCarrierProfileClient;

public class CommunicationResourceFactory {

    private final CarrierCommunicationService carrierCommunicationService;

    public CommunicationResourceFactory() {
        CarrierCommunicationClient carrierCommunicationClient =
            CarrierMailClient.getInstance();
        CarrierProfileResource carrierProfileResource =
            new CarrierProfileResourceFactory().create();
        CarrierProfileClient carrierProfileClient = new InternalCarrierProfileClient(
            carrierProfileResource
        );
        this.carrierCommunicationService =
            new CarrierCommunicationService(
                carrierCommunicationClient,
                carrierProfileClient
            );
    }

    public CommunicationResource create() {
        return new CommunicationResource(carrierCommunicationService);
    }
}

package ca.ulaval.glo4003.repul.shipment.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.repul.communication.api.CommunicationResource;
import ca.ulaval.glo4003.repul.communication.api.config.CommunicationResourceFactory;
import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.application.ShipmentService;
import ca.ulaval.glo4003.repul.shipment.domain.CarrierCommunicationClient;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemRepository;
import ca.ulaval.glo4003.repul.shipment.infra.InternalCarrierCommunicationClient;

public class ShipmentResourceFactory {

    private final ShipmentService shipmentService;

    public ShipmentResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        ShipmentItemRepository shipmentItemRepository = serviceLocator.getService(
            ShipmentItemRepository.class
        );
        CommunicationResource communicationResource = new CommunicationResourceFactory()
            .create();
        CarrierCommunicationClient carrierCommunicationClient =
            new InternalCarrierCommunicationClient(communicationResource);
        this.shipmentService =
            new ShipmentService(shipmentItemRepository, carrierCommunicationClient);
    }

    public ShipmentResource create() {
        return new ShipmentResource(shipmentService);
    }
}

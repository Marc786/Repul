package ca.ulaval.glo4003.repul.communication.api;

import ca.ulaval.glo4003.repul.communication.api.assembler.ShipmentInfoAssembler;
import ca.ulaval.glo4003.repul.communication.api.dto.request.ShipmentInfoRequest;
import ca.ulaval.glo4003.repul.communication.application.CarrierCommunicationService;
import ca.ulaval.glo4003.repul.communication.domain.ShipmentInfo;
import ca.ulaval.glo4003.repul.communication.domain.ShipmentItemAndLocation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CommunicationResource {

    private final CarrierCommunicationService carrierCommunicationService;
    private final ShipmentInfoAssembler shipmentInfoAssembler;

    public CommunicationResource(
        CarrierCommunicationService carrierCommunicationService
    ) {
        this.carrierCommunicationService = carrierCommunicationService;
        this.shipmentInfoAssembler = new ShipmentInfoAssembler();
    }

    public void sendEmailsToShipmentCarriers(
        @NotNull @Valid ShipmentInfoRequest shipmentInfoRequest
    ) {
        List<ShipmentItemAndLocation> shipmentItemsAndLocation =
            shipmentInfoAssembler.toShipmentItemsAndLocation(
                shipmentInfoRequest.shipmentItemsAndLocation()
            );

        ShipmentInfo shipmentInfo = new ShipmentInfo(
            shipmentInfoRequest.shipmentId(),
            shipmentInfoRequest.pickUpPointLocation(),
            shipmentItemsAndLocation
        );

        carrierCommunicationService.sendEmailToShipmentCarriers(shipmentInfo);
    }
}

package ca.ulaval.glo4003.repul.shipment.application;

import static ca.ulaval.glo4003.constant.Constants.ShipmentOrigin.SHIPMENT_PICKUP_POINT_ORIGIN;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.domain.*;
import java.time.LocalDate;
import java.util.List;

public class ShipmentService {

    private final ShipmentItemRepository shipmentItemRepository;
    private final CarrierCommunicationClient carrierCommunicationClient;

    public ShipmentService(
        ShipmentItemRepository shipmentItemRepository,
        CarrierCommunicationClient carrierCommunicationClient
    ) {
        this.shipmentItemRepository = shipmentItemRepository;
        this.carrierCommunicationClient = carrierCommunicationClient;
    }

    public void addConfirmedShipmentItem(
        ShipmentItemId shipmentItemId,
        PickupPointLocation pickUpPointLocation,
        LocalDate deliveryDate
    ) {
        ShipmentItem shipmentItem = new ShipmentItem(
            shipmentItemId,
            pickUpPointLocation,
            deliveryDate
        );

        shipmentItemRepository.save(shipmentItem);
    }

    public ShipmentId createShipment(LocalDate lowerBoundDate, LocalDate upperBoundDate) {
        ShipmentItems shipmentItemsInRange = shipmentItemRepository
            .findAll()
            .findAssembledShipmentItems()
            .findByDeliveryDate(lowerBoundDate, upperBoundDate);

        Shipment shipment = new Shipment(
            new ShipmentId(),
            shipmentItemsInRange.getItems(),
            SHIPMENT_PICKUP_POINT_ORIGIN
        );

        shipment.notifyShipmentCreated(carrierCommunicationClient);
        shipmentItemsInRange.setStatusAsShipped();
        shipmentItemsInRange.getItems().forEach(shipmentItemRepository::save);

        return shipment.getId();
    }

    public void addAssembledShipmentItems(List<ShipmentItemId> shipmentItemIds) {
        shipmentItemIds.forEach(this::addAssembledShipmentItem);
    }

    private void addAssembledShipmentItem(ShipmentItemId shipmentItemId) {
        ShipmentItem shipmentItem = shipmentItemRepository.findById(shipmentItemId);
        shipmentItem.setStatusAsAssembled();

        shipmentItemRepository.save(shipmentItem);
    }
}

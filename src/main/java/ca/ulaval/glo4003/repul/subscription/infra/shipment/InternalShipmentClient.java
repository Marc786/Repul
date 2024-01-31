package ca.ulaval.glo4003.repul.subscription.infra.shipment;

import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.ConfirmedShipmentItemRequest;
import ca.ulaval.glo4003.repul.subscription.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;

public class InternalShipmentClient implements ShipmentClient {

    private final ShipmentResource shipmentResource;
    private final AddConfirmedShipmentItemAssembler addConfirmedShipmentItemAssembler =
        new AddConfirmedShipmentItemAssembler();

    public InternalShipmentClient(ShipmentResource shipmentResource) {
        this.shipmentResource = shipmentResource;
    }

    @Override
    public void addConfirmedMealKitShipment(MealKit mealKit) {
        ConfirmedShipmentItemRequest confirmedShipmentItemRequest =
            addConfirmedShipmentItemAssembler.toRequest(
                mealKit.getId(),
                mealKit.getPickupPointLocation(),
                mealKit.getDeliveryDate()
            );
        shipmentResource.addConfirmedShipmentItem(confirmedShipmentItemRequest);
    }
}

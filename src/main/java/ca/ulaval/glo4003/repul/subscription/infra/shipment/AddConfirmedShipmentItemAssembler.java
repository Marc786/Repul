package ca.ulaval.glo4003.repul.subscription.infra.shipment;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.ConfirmedShipmentItemRequest;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitId;
import java.time.LocalDate;

public class AddConfirmedShipmentItemAssembler {

    public ConfirmedShipmentItemRequest toRequest(
        MealKitId mealKitId,
        PickupPointLocation pickupPointLocation,
        LocalDate deliveryDate
    ) {
        return new ConfirmedShipmentItemRequest(
            mealKitId.toString(),
            pickupPointLocation.toString(),
            deliveryDate.toString()
        );
    }
}

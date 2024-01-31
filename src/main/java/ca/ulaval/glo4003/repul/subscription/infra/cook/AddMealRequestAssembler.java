package ca.ulaval.glo4003.repul.subscription.infra.cook;

import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.request.AddMealRequest;

public class AddMealRequestAssembler {

    public AddMealRequest toRequest(
        String mealKitId,
        String mealKitType,
        String deliveryDate
    ) {
        return new AddMealRequest(mealKitId, mealKitType, deliveryDate);
    }
}

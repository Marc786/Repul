package ca.ulaval.glo4003.repul.subscription.api.assembler;

import ca.ulaval.glo4003.repul.subscription.api.dto.response.MealKitResponse;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import java.util.List;

public class MealKitAssembler {

    public List<MealKitResponse> toResponse(List<MealKit> mealKits) {
        return mealKits.stream().map(this::toResponse).toList();
    }

    private MealKitResponse toResponse(MealKit mealKit) {
        return new MealKitResponse(
            mealKit.getId().toString(),
            mealKit.getMealKitType().toString(),
            mealKit.getDeliveryDate().toString(),
            mealKit.getPickupPointLocation().toString(),
            mealKit.getSubscriberConfirmationStatus().toString()
        );
    }
}

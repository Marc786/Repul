package ca.ulaval.glo4003.repul.subscription.infra.cook;

import ca.ulaval.glo4003.repul.kitchen.api.meal.MealResource;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.request.AddMealRequest;
import ca.ulaval.glo4003.repul.subscription.domain.MealClient;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;

public class InternalMealClient implements MealClient {

    private final AddMealRequestAssembler addMealRequestAssembler =
        new AddMealRequestAssembler();
    private final MealResource mealResource;

    public InternalMealClient(MealResource mealResource) {
        this.mealResource = mealResource;
    }

    @Override
    public void addMealToPrepare(MealKit mealKit) {
        AddMealRequest addMealRequest = addMealRequestAssembler.toRequest(
            mealKit.getId().toString(),
            mealKit.getMealKitType().toString(),
            mealKit.getDeliveryDate().toString()
        );

        mealResource.addMealToPrepare(addMealRequest);
    }
}

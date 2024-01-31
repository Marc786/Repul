package ca.ulaval.glo4003.small.repul.subscription.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.subscription.MealKitFixture;
import ca.ulaval.glo4003.repul.subscription.api.assembler.MealKitAssembler;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.MealKitResponse;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import java.util.List;
import org.junit.jupiter.api.Test;

class MealKitAssemblerTest {

    private final MealKitFixture mealKitFixture = new MealKitFixture();
    private final MealKit mealKit1 = mealKitFixture.build();
    private final MealKit mealKit2 = mealKitFixture.withOtherId().build();
    private final List<MealKit> mealKits = List.of(mealKit1, mealKit2);
    private final MealKitAssembler mealKitAssembler = new MealKitAssembler();

    @Test
    void toResponse_returnsMealKitsResponse() {
        List<MealKitResponse> expectedMealKitsResponse = List.of(
            new MealKitResponse(
                mealKit1.getId().toString(),
                mealKit1.getMealKitType().toString(),
                mealKit1.getDeliveryDate().toString(),
                mealKit1.getPickupPointLocation().toString(),
                mealKit1.getSubscriberConfirmationStatus().toString()
            ),
            new MealKitResponse(
                mealKit2.getId().toString(),
                mealKit2.getMealKitType().toString(),
                mealKit2.getDeliveryDate().toString(),
                mealKit2.getPickupPointLocation().toString(),
                mealKit2.getSubscriberConfirmationStatus().toString()
            )
        );

        List<MealKitResponse> actualMealKitsResponse = mealKitAssembler.toResponse(
            mealKits
        );

        assertEquals(expectedMealKitsResponse, actualMealKitsResponse);
    }
}

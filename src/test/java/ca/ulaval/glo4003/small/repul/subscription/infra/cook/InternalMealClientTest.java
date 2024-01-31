package ca.ulaval.glo4003.small.repul.subscription.infra.cook;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.fixture.subscription.MealKitFixture;
import ca.ulaval.glo4003.repul.kitchen.api.meal.MealResource;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.request.AddMealRequest;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.infra.cook.InternalMealClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InternalMealClientTest {

    private final MealKitFixture mealKitFixture = new MealKitFixture();
    private final MealResource mealResourceMock = mock(MealResource.class);

    private InternalMealClient internalMealClient;

    @BeforeEach
    void setup() {
        internalMealClient = new InternalMealClient(mealResourceMock);
    }

    @Test
    void addMealToPrepare_addMealToPrepareIsCalled() {
        MealKit mealKit = mealKitFixture.build();
        AddMealRequest expectedRequest = new AddMealRequest(
            mealKit.getId().toString(),
            mealKit.getMealKitType().toString(),
            mealKit.getDeliveryDate().toString()
        );

        internalMealClient.addMealToPrepare(mealKit);

        verify(mealResourceMock).addMealToPrepare(expectedRequest);
    }
}

package ca.ulaval.glo4003.small.repul.kitchen.api.meal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.fixture.kitchen.IngredientsResponseFixture;
import ca.ulaval.glo4003.fixture.kitchen.MealFixture;
import ca.ulaval.glo4003.fixture.kitchen.MealsResponseFixture;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.repul.kitchen.api.meal.MealResource;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.request.AddMealRequest;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientsResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealsResponse;
import ca.ulaval.glo4003.repul.kitchen.application.MealService;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meals;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealResourceTest {

    private static final Meal MEAL = new MealFixture().build();
    private static final Meals MEALS = new Meals(List.of(MEAL));
    private static final MealsResponse MEALS_RESPONSE = new MealsResponseFixture()
        .withMeal(MEAL)
        .build();
    private static final IngredientsResponse INGREDIENTS_RESPONSE =
        new IngredientsResponseFixture().withIngredients(MEAL.getIngredients()).build();

    private static final LocalDate DATE = MEAL.getDeliveryDate();
    private static final LocalDate FUTURE_DATE = DATE.plusDays(4);
    private static final String mealKitTypeString = "standard";
    private static final MealKitType MEAL_KIT_TYPE = MealKitType.fromString(
        mealKitTypeString
    );
    private static final AddMealRequest ADD_MEAL_REQUEST = new AddMealRequest(
        MEAL.getMealId().toString(),
        mealKitTypeString,
        MEAL.getDeliveryDate().toString()
    );

    private static final MealService mealService = mock(MealService.class);
    private MealResource mealResource;

    @BeforeEach
    void setup() {
        mealResource = new MealResource(mealService);
    }

    @Test
    void addMealToPrepare_mealIsAddedToPrepare() {
        mealResource.addMealToPrepare(ADD_MEAL_REQUEST);

        verify(mealService)
            .addMealToPrepare(MEAL.getMealId(), MEAL_KIT_TYPE, MEAL.getDeliveryDate());
    }

    @Test
    void getMealsToPrepare_mealsToPrepareAreReturned() {
        when(mealService.getMealsToPrepare(any(), eq(FUTURE_DATE))).thenReturn(MEALS);
        Response expectedResponse = Response.ok(MEALS_RESPONSE).build();

        Response actualResponse = mealResource.getMealsToPrepare(FUTURE_DATE.toString());

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getEntity(), actualResponse.getEntity());
    }

    @Test
    void getMealsToPrepareIngredients_mealsToPrepareIngredientsAreReturned() {
        when(mealService.getMealsToPrepare(any(), eq(FUTURE_DATE))).thenReturn(MEALS);
        Response expectedResponse = Response.ok(INGREDIENTS_RESPONSE).build();

        Response actualResponse = mealResource.getMealsToPrepareIngredients(
            FUTURE_DATE.toString()
        );

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getEntity(), actualResponse.getEntity());
    }
}

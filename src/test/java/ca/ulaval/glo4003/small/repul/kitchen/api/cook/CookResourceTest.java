package ca.ulaval.glo4003.small.repul.kitchen.api.cook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.fixture.kitchen.MealFixture;
import ca.ulaval.glo4003.fixture.kitchen.MealsResponseFixture;
import ca.ulaval.glo4003.repul.kitchen.api.cook.CookResource;
import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.request.MealIdsRequest;
import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.response.MealIdsResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealsResponse;
import ca.ulaval.glo4003.repul.kitchen.application.CookService;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookResourceTest {

    private static final String COOK_ID_STRING = UUID.randomUUID().toString();
    private static final CookId COOK_ID = new CookId(COOK_ID_STRING);
    private static final Meal MEAL = new MealFixture().build();
    private static final MealId MEAL_ID = MEAL.getMealId();
    private static final List<MealId> MEAL_IDS = List.of(MEAL_ID);
    private static final String MEAL_ID_STRING = MEAL_ID.toString();
    private static final List<String> MEAL_IDS_STRING = List.of(MEAL_ID_STRING);
    private static final MealIdsRequest MEAL_IDS_REQUEST = new MealIdsRequest(
        MEAL_IDS_STRING
    );
    private static final MealIdsResponse MEAL_IDS_RESPONSE = new MealIdsResponse(
        MEAL_IDS_STRING
    );
    private static final List<Meal> MEALS = List.of(MEAL);
    private static final MealsResponse MEALS_RESPONSE = new MealsResponseFixture()
        .withMeal(MEAL)
        .build();
    private static final CookService cookServiceMock = mock(CookService.class);
    private CookResource cookResource;

    @BeforeEach
    void setup() {
        cookResource = new CookResource(cookServiceMock);
    }

    @Test
    void assignCookToMeal_cookIsAssignedToMeal() {
        when(cookServiceMock.assignCookToMeals(COOK_ID, MEAL_IDS)).thenReturn(MEAL_IDS);
        Response expectedResponse = Response.ok(MEAL_IDS_RESPONSE).build();

        Response actualResponse = cookResource.assignCookToMeal(
            COOK_ID_STRING,
            MEAL_IDS_REQUEST
        );

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getEntity(), actualResponse.getEntity());
    }

    @Test
    void unassignCookFromMeal_cookIsUnassignedFromMeal() {
        when(cookServiceMock.unassignCookFromMeal(COOK_ID, MEAL_IDS))
            .thenReturn(MEAL_IDS);
        Response expectedResponse = Response.ok(MEAL_IDS_RESPONSE).build();

        Response actualResponse = cookResource.unassignCookFromMeal(
            COOK_ID_STRING,
            MEAL_IDS_REQUEST
        );

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getEntity(), actualResponse.getEntity());
    }

    @Test
    void assembleMeal_returnsExpectedResponse() {
        when(cookServiceMock.assembleMeal(COOK_ID, MEAL_IDS)).thenReturn(MEAL_IDS);
        Response expectedResponse = Response.ok(MEAL_IDS_RESPONSE).build();

        Response actualResponse = cookResource.assembleMeal(
            COOK_ID_STRING,
            MEAL_IDS_REQUEST
        );

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getEntity(), actualResponse.getEntity());
    }

    @Test
    void getAssignedMeals_returnsExpectedResponse() {
        when(cookServiceMock.getAssignedMeals(COOK_ID)).thenReturn(MEALS);
        Response expectedResponse = Response.ok(MEALS_RESPONSE).build();

        Response actualResponse = cookResource.getAssignedMeals(COOK_ID_STRING);

        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getEntity(), actualResponse.getEntity());
    }
}

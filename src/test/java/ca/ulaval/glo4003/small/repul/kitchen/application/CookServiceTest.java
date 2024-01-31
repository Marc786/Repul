package ca.ulaval.glo4003.small.repul.kitchen.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.fixture.kitchen.CookFixture;
import ca.ulaval.glo4003.fixture.kitchen.MealFixture;
import ca.ulaval.glo4003.repul.kitchen.application.CookService;
import ca.ulaval.glo4003.repul.kitchen.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.Cook;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookRepository;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.MealFinder;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.infra.cook.exception.CookNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookServiceTest {

    private static final String COOK_ID_STRING = UUID.randomUUID().toString();
    private static final CookId COOK_ID = new CookId(COOK_ID_STRING);
    private static final Cook COOK = new CookFixture().withId(COOK_ID).build();
    private static final Meal MEAL = new MealFixture().build();
    private static final List<MealId> MEAL_IDS = List.of(MEAL.getMealId());
    private final CookRepository cookRepositoryMock = mock(CookRepository.class);
    private final MealFinder mealFinderMock = mock(MealFinder.class);
    private final ShipmentClient shipmentClientMock = mock(ShipmentClient.class);
    private CookService cookService;

    @BeforeEach
    void setup() {
        cookService =
            new CookService(cookRepositoryMock, mealFinderMock, shipmentClientMock);
    }

    @Test
    void existingCook_assignCookToMeals_updatedCookIsSaved() {
        when(cookRepositoryMock.findById(COOK_ID)).thenReturn(COOK);

        cookService.assignCookToMeals(COOK_ID, MEAL_IDS);

        verify(cookRepositoryMock).save(COOK);
    }

    @Test
    void nonExistingCook_assignCookToMeals_newCookIsCreated() {
        when(cookRepositoryMock.findById(COOK_ID))
            .thenThrow(new CookNotFoundException(COOK_ID));

        assertDoesNotThrow(() -> cookService.assignCookToMeals(COOK_ID, MEAL_IDS));
    }

    @Test
    void assembleMeal_shipmentClientIsCalled() {
        when(cookRepositoryMock.findById(COOK_ID)).thenReturn(COOK);
        when(mealFinderMock.findById(MEAL.getMealId())).thenReturn(MEAL);
        COOK.assignMeals(mealFinderMock, MEAL_IDS);

        cookService.assembleMeal(COOK_ID, MEAL_IDS);

        verify(shipmentClientMock).addAssembledShipmentItem(MEAL_IDS);
    }
}

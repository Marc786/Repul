package ca.ulaval.glo4003.small.repul.kitchen.domain.cook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.fixture.kitchen.MealFixture;
import ca.ulaval.glo4003.repul.kitchen.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.Cook;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.MealFinder;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealPreparationStatus;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.exception.MealNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookTest {

    private static final String COOK_ID_STRING = UUID.randomUUID().toString();
    private static final CookId COOK_ID = new CookId(COOK_ID_STRING);
    private static final String MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final String MEAL_ID_STRING_INVALID = UUID.randomUUID().toString();
    private static final MealId MEAL_ID = new MealId(MEAL_ID_STRING);
    private static final MealId MEAL_ID_INVALID = new MealId(MEAL_ID_STRING_INVALID);
    private static final List<MealId> MEAL_IDS = List.of(MEAL_ID);
    private static final List<MealId> MEAL_IDS_WITH_INVALID = List.of(
        MEAL_ID_INVALID,
        MEAL_ID
    );
    private static final Meal MEAL = new MealFixture().withMealId(MEAL_ID).build();
    private static final Meal MEAL_INVALID = new MealFixture()
        .withMealId(MEAL_ID_INVALID)
        .build();
    private final ShipmentClient shipmentClientMock = mock(ShipmentClient.class);
    private final MealFinder mealFinderMock = mock(MealFinder.class);
    private Cook cook;

    @BeforeEach
    void setup() {
        cook = new Cook(COOK_ID);
    }

    @AfterEach
    void teardown() {
        reset(shipmentClientMock, mealFinderMock);
    }

    @Test
    void mealNotAssigned_assignMeal_mealIsAssigned() {
        when(mealFinderMock.findById(MEAL_ID)).thenReturn(MEAL);

        cook.assignMeals(mealFinderMock, MEAL_IDS);

        assertTrue(cook.getAssignedMeals().contains(MEAL));
    }

    @Test
    void mealAssigned_assignMeal_mealIsNotAssigned() {
        when(mealFinderMock.findById(MEAL_ID)).thenReturn(MEAL);
        int expectedAssignedMealsSize = 1;
        cook.assignMeals(mealFinderMock, MEAL_IDS);

        cook.assignMeals(mealFinderMock, MEAL_IDS);

        List<Meal> actualAssignedMeals = cook.getAssignedMeals();
        assertEquals(expectedAssignedMealsSize, actualAssignedMeals.size());
    }

    @Test
    void mealDoesntExist_assignMeal_mealIsNotAssigned() {
        when(mealFinderMock.findById(MEAL_ID)).thenReturn(MEAL);
        when(mealFinderMock.findById(MEAL_ID_INVALID))
            .thenThrow(MealNotFoundException.class);

        List<MealId> actualMealIds = cook.assignMeals(
            mealFinderMock,
            MEAL_IDS_WITH_INVALID
        );

        List<Meal> actualAssignedMeals = cook.getAssignedMeals();
        assertEquals(MEAL_IDS, actualMealIds);
        assertEquals(List.of(MEAL), actualAssignedMeals);
    }

    @Test
    void mealAssigned_unassignMeal_mealIsNotAssigned() {
        when(mealFinderMock.findById(MEAL_ID)).thenReturn(MEAL);
        cook.assignMeals(mealFinderMock, MEAL_IDS);

        cook.unassignMeals(MEAL_IDS);

        List<Meal> actualAssignedMeals = cook.getAssignedMeals();
        assertThat(actualAssignedMeals).isEmpty();
    }

    @Test
    void mealDoesntExist_unassignMeal_allMealsAreUnassigned() {
        when(mealFinderMock.findById(MEAL_ID)).thenReturn(MEAL);
        when(mealFinderMock.findById(MEAL_ID_INVALID)).thenReturn(MEAL_INVALID);
        cook.assignMeals(mealFinderMock, MEAL_IDS_WITH_INVALID);
        cook.unassignMeals(List.of(MEAL_ID_INVALID));

        List<MealId> actualMealIds = cook.unassignMeals(MEAL_IDS_WITH_INVALID);

        List<Meal> actualAssignedMeals = cook.getAssignedMeals();
        assertEquals(MEAL_IDS, actualMealIds);
        assertThat(actualAssignedMeals).isEmpty();
    }

    @Test
    void mealNotAssigned_unassignMeal_mealIsNotAssigned() {
        cook.unassignMeals(MEAL_IDS);

        List<Meal> actualAssignedMeals = cook.getAssignedMeals();
        assertThat(actualAssignedMeals).isEmpty();
    }

    @Test
    void assembleMeals_mealsAreAssembled() {
        when(mealFinderMock.findById(MEAL_ID)).thenReturn(MEAL);
        cook.assignMeals(mealFinderMock, MEAL_IDS);

        cook.assembleMeals(MEAL_IDS, shipmentClientMock);

        assertEquals(MEAL.getMealPreparationStatus(), MealPreparationStatus.ASSEMBLED);
        verify(shipmentClientMock).addAssembledShipmentItem(MEAL_IDS);
    }

    @Test
    void mealNotAssigned_assembleMeals_validMealsAreAssembled() {
        Meal meal = new MealFixture().build();
        List<MealId> mealIds = List.of(meal.getMealId());
        List<MealId> mealIdsWithInvalid = List.of(MEAL_ID_INVALID, meal.getMealId());
        when(mealFinderMock.findById(meal.getMealId())).thenReturn(meal);
        cook.assignMeals(mealFinderMock, mealIds);

        List<MealId> actualMealIds = cook.assembleMeals(
            mealIdsWithInvalid,
            shipmentClientMock
        );

        assertEquals(mealIds, actualMealIds);
        verify(shipmentClientMock).addAssembledShipmentItem(mealIds);
    }
}

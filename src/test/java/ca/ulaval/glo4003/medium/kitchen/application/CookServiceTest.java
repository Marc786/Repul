package ca.ulaval.glo4003.medium.kitchen.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.fixture.kitchen.CookFixture;
import ca.ulaval.glo4003.fixture.kitchen.MealFixture;
import ca.ulaval.glo4003.repul.kitchen.application.CookService;
import ca.ulaval.glo4003.repul.kitchen.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.Cook;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookRepository;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealRepository;
import ca.ulaval.glo4003.repul.kitchen.infra.cook.InMemoryCookRepository;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.InMemoryMealRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CookServiceTest {

    private static final Cook COOK = new CookFixture().build();
    private static final CookId COOK_ID = COOK.getCookId();
    private static final Meal MEAL = new MealFixture().build();
    private static final List<MealId> MEAL_IDS = List.of(MEAL.getMealId());
    private final CookRepository cookRepository = new InMemoryCookRepository();
    private final MealRepository mealRepository = new InMemoryMealRepository();
    private final ShipmentClient shipmentClientMock = mock(ShipmentClient.class);
    private CookService cookService;

    @BeforeEach
    void setup() {
        cookService = new CookService(cookRepository, mealRepository, shipmentClientMock);
    }

    @Test
    void assignCookToMeals_mealsAreAssignedToCook() {
        cookRepository.save(COOK);
        mealRepository.save(MEAL);

        cookService.assignCookToMeals(COOK_ID, MEAL_IDS);

        List<Meal> assignedMeals = cookRepository.findById(COOK_ID).getAssignedMeals();
        assertThat(assignedMeals).usingRecursiveComparison().isEqualTo(List.of(MEAL));
    }

    @Test
    void nonExistingCook_assignCookToMeals_mealsAreAssignedToCook() {
        mealRepository.save(MEAL);

        List<MealId> mealIds = cookService.assignCookToMeals(COOK_ID, MEAL_IDS);

        assertEquals(MEAL_IDS, mealIds);
    }

    @Test
    void unassignCookFromMeal_mealsAreUnassignedFromCook() {
        cookRepository.save(COOK);
        mealRepository.save(MEAL);
        cookService.assignCookToMeals(COOK_ID, MEAL_IDS);

        cookService.unassignCookFromMeal(COOK_ID, MEAL_IDS);

        assertTrue(cookRepository.findById(COOK_ID).getAssignedMeals().isEmpty());
    }

    @Test
    void nonExistingCook_unassignCookFromMeal_mealsAreUnassignedFromCook() {
        mealRepository.save(MEAL);
        int expectedSize = 0;

        List<MealId> mealIds = cookService.unassignCookFromMeal(COOK_ID, MEAL_IDS);

        assertEquals(expectedSize, mealIds.size());
    }

    @Test
    void assembleMeal_mealsAreAssembledByCook() {
        cookRepository.save(COOK);
        mealRepository.save(MEAL);
        cookService.assignCookToMeals(COOK_ID, MEAL_IDS);

        List<MealId> mealIds = cookService.assembleMeal(COOK_ID, MEAL_IDS);

        assertTrue(mealIds.contains(MEAL.getMealId()));
    }

    @Test
    void nonExistingCook_assembleMeal_mealIsAssignedToCook() {
        List<MealId> mealIds = cookService.assembleMeal(COOK_ID, MEAL_IDS);

        assertTrue(mealIds.isEmpty());
    }

    @Test
    void getAssignedMeals_mealsAreReturned() {
        cookRepository.save(COOK);
        mealRepository.save(MEAL);
        cookService.assignCookToMeals(COOK_ID, MEAL_IDS);

        List<Meal> meals = cookService.getAssignedMeals(COOK_ID);

        assertThat(meals).usingRecursiveComparison().isEqualTo(List.of(MEAL));
    }

    @Test
    void nonExistingCook_getAssignedMeals_returnsEmptyList() {
        List<Meal> meals = cookService.getAssignedMeals(COOK_ID);

        assertTrue(meals.isEmpty());
    }
}

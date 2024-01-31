package ca.ulaval.glo4003.small.repul.kitchen.infra.meal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.fixture.kitchen.MealFixture;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.InMemoryMealRepository;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.exception.MealNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryMealRepositoryTest {

    private static final LocalDate MEAL_DATE = LocalDate.of(2020, 3, 3);
    private static final String MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final MealId MEAL_ID = new MealId(MEAL_ID_STRING);
    private final Meal meal = new MealFixture()
        .withMealId(MEAL_ID)
        .withDeliveryDate(MEAL_DATE)
        .build();
    private InMemoryMealRepository inMemoryMealRepository;

    @BeforeEach
    void setup() {
        inMemoryMealRepository = new InMemoryMealRepository();
    }

    @Test
    void save_shouldSaveMeal() {
        inMemoryMealRepository.save(meal);

        Meal actualMeal = inMemoryMealRepository.findById(MEAL_ID);

        assertThat(actualMeal).usingRecursiveComparison().isEqualTo(meal);
    }

    @Test
    void findById_shouldReturnMeal() {
        inMemoryMealRepository.save(meal);

        Meal actualMeal = inMemoryMealRepository.findById(MEAL_ID);

        assertThat(actualMeal).usingRecursiveComparison().isEqualTo(meal);
    }

    @Test
    void findAll_shouldReturnAllMeals() {
        List<Meal> expectedMeals = List.of(meal);
        inMemoryMealRepository.save(meal);

        List<Meal> actualMeals = inMemoryMealRepository.findAll();

        assertThat(actualMeals).usingRecursiveComparison().isEqualTo(expectedMeals);
    }

    @Test
    void mealIdDoesNotExists_findById_throwsMealNotFoundException() {
        MealId mealId = new MealId(UUID.randomUUID().toString());

        assertThrows(
            MealNotFoundException.class,
            () -> inMemoryMealRepository.findById(mealId)
        );
    }
}

package ca.ulaval.glo4003.medium.kitchen.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ca.ulaval.glo4003.fixture.kitchen.MealFixture;
import ca.ulaval.glo4003.lib.catalog.MealKitCatalog;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.finder.MealRecipeFinder;
import ca.ulaval.glo4003.repul.kitchen.application.MealService;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealFactory;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealRepository;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meals;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.InMemoryMealRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MealServiceTest {

    private static final Meal MEAL = new MealFixture().build();
    private static final MealRecipeFinder MEAL_RECIPE_FINDER = new MealKitCatalog();
    private static final MealKitType MEAL_KIT_TYPE = MealKitType.STANDARD;
    private static final MealFactory MEAL_FACTORY = new MealFactory(MEAL_RECIPE_FINDER);
    private static final MealRepository MEAL_REPOSITORY = new InMemoryMealRepository();
    private static final LocalDate START_DATE = MEAL.getDeliveryDate().minusDays(1);
    private static final LocalDate END_DATE = MEAL.getDeliveryDate().plusDays(1);
    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealService = new MealService(MEAL_FACTORY, MEAL_REPOSITORY);
    }

    @Test
    void addMealToPrepare_mealIsSaved() {
        mealService.addMealToPrepare(
            MEAL.getMealId(),
            MEAL_KIT_TYPE,
            MEAL.getDeliveryDate()
        );

        assertThat(MEAL_REPOSITORY.findById(MEAL.getMealId()))
            .usingRecursiveComparison()
            .isEqualTo(MEAL);
    }

    @Test
    void getMealsToPrepare_mealsAreFilteredByDate() {
        MEAL_REPOSITORY.save(MEAL);
        Meals expectedMeals = new Meals(List.of(MEAL));

        Meals actualMeals = mealService.getMealsToPrepare(START_DATE, END_DATE);

        assertThat(actualMeals).usingRecursiveComparison().isEqualTo(expectedMeals);
    }
}

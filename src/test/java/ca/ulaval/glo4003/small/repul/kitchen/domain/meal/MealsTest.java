package ca.ulaval.glo4003.small.repul.kitchen.domain.meal;

import static org.assertj.core.api.Assertions.assertThat;

import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meals;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealsTest {

    private static final String A_MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final String ANOTHER_MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final MealId A_MEAL_ID = new MealId(A_MEAL_ID_STRING);
    private static final MealId ANOTHER_MEAL_ID = new MealId(ANOTHER_MEAL_ID_STRING);
    private static final LocalDate A_DELIVERY_DATE = LocalDate.of(2021, 1, 15);
    private static final LocalDate ANOTHER_DELIVERY_DATE = LocalDate.of(2022, 1, 2);
    private static final Meal A_MEAL = new Meal(A_MEAL_ID, A_DELIVERY_DATE, List.of());
    private static final Meal ANOTHER_MEAL = new Meal(
        ANOTHER_MEAL_ID,
        ANOTHER_DELIVERY_DATE,
        List.of()
    );
    private static final LocalDate A_START_DATE = LocalDate.of(2021, 1, 1);
    private static final LocalDate AN_END_DATE = LocalDate.of(2021, 12, 31);

    private Meals meals;

    @BeforeEach
    void setup() {
        meals = new Meals(List.of(A_MEAL, ANOTHER_MEAL));
    }

    @Test
    void filterByDeliveryDate_returnsMealsWithDeliveryDateBetweenStartDateAndEndDate() {
        List<Meal> filteredMeals = meals.filterByDeliveryDate(A_START_DATE, AN_END_DATE);

        assertThat(filteredMeals).containsExactly(A_MEAL);
        assertThat(filteredMeals).doesNotContain(ANOTHER_MEAL);
    }
}

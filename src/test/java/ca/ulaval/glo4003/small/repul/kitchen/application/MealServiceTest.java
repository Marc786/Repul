package ca.ulaval.glo4003.small.repul.kitchen.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.repul.kitchen.application.MealService;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealServiceTest {

    private static final String MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final MealId MEAL_ID = new MealId(MEAL_ID_STRING);
    private static final MealKitType MEAL_KIT_TYPE = MealKitType.STANDARD;
    private static final LocalDate START_DATE = LocalDate.of(2021, 3, 3);
    private static final LocalDate DELIVERY_DATE = START_DATE.plusDays(1);
    private static final LocalDate END_DATE = DELIVERY_DATE.plusDays(2);
    private static final Meal MEAL = new Meal(MEAL_ID, DELIVERY_DATE, List.of());
    private static final Meals MEALS = new Meals(List.of(MEAL));
    private static final MealFactory mealFactoryMock = mock(MealFactory.class);
    private static final MealRepository mealRepositoryMock = mock(MealRepository.class);
    private MealService mealService;

    @BeforeEach
    void setup() {
        mealService = new MealService(mealFactoryMock, mealRepositoryMock);
    }

    @Test
    void addMealToPrepare_mealIsAddedToRepository() {
        when(mealFactoryMock.create(MEAL_ID, MEAL_KIT_TYPE, DELIVERY_DATE))
            .thenReturn(MEAL);

        mealService.addMealToPrepare(MEAL_ID, MEAL_KIT_TYPE, DELIVERY_DATE);

        verify(mealRepositoryMock).save(MEAL);
    }

    @Test
    void getMealsToPrepare_mealsAreFilteredByDeliveryDate() {
        when(mealRepositoryMock.findAll()).thenReturn(List.of(MEAL));

        Meals actualMeals = mealService.getMealsToPrepare(START_DATE, END_DATE);

        assertThat(actualMeals).usingRecursiveComparison().isEqualTo(MEALS);
    }
}

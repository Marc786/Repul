package ca.ulaval.glo4003.repul.kitchen.application;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.*;
import java.time.LocalDate;

public class MealService {

    private final MealFactory mealFactory;
    private final MealRepository mealRepository;

    public MealService(MealFactory mealFactory, MealRepository mealRepository) {
        this.mealFactory = mealFactory;
        this.mealRepository = mealRepository;
    }

    public void addMealToPrepare(
        MealId mealId,
        MealKitType mealKitType,
        LocalDate deliveryDate
    ) {
        Meal meal = mealFactory.create(mealId, mealKitType, deliveryDate);

        mealRepository.save(meal);
    }

    public Meals getMealsToPrepare(LocalDate startDate, LocalDate endDate) {
        Meals meals = new Meals(mealRepository.findAll());
        return new Meals(meals.filterByDeliveryDate(startDate, endDate));
    }
}

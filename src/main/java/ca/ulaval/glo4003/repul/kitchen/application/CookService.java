package ca.ulaval.glo4003.repul.kitchen.application;

import ca.ulaval.glo4003.repul.kitchen.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.Cook;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookRepository;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.MealFinder;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.infra.cook.exception.CookNotFoundException;
import java.util.List;

public class CookService {

    private final CookRepository cookRepository;
    private final MealFinder mealFinder;
    private final ShipmentClient shipmentClient;

    public CookService(
        CookRepository cookRepository,
        MealFinder mealFinder,
        ShipmentClient shipmentClient
    ) {
        this.cookRepository = cookRepository;
        this.mealFinder = mealFinder;
        this.shipmentClient = shipmentClient;
    }

    public List<MealId> assignCookToMeals(CookId cookId, List<MealId> mealIds) {
        Cook cook = findOrCreateCook(cookId);

        List<MealId> assignedMealIds = cook.assignMeals(mealFinder, mealIds);

        cookRepository.save(cook);

        return assignedMealIds;
    }

    public List<MealId> unassignCookFromMeal(CookId cookId, List<MealId> mealIds) {
        Cook cook;
        try {
            cook = cookRepository.findById(cookId);
        } catch (CookNotFoundException e) {
            return List.of();
        }

        List<MealId> unassignedMealIds = cook.unassignMeals(mealIds);

        cookRepository.save(cook);

        return unassignedMealIds;
    }

    public List<MealId> assembleMeal(CookId cookId, List<MealId> mealIds) {
        Cook cook = findOrCreateCook(cookId);

        List<MealId> assembledMealIds = cook.assembleMeals(mealIds, shipmentClient);

        cookRepository.save(cook);

        return assembledMealIds;
    }

    public List<Meal> getAssignedMeals(CookId cookId) {
        Cook cook;
        try {
            cook = cookRepository.findById(cookId);
        } catch (CookNotFoundException e) {
            return List.of();
        }

        return cook.getAssignedMeals();
    }

    private Cook findOrCreateCook(CookId cookId) {
        Cook cook;
        try {
            cook = cookRepository.findById(cookId);
        } catch (CookNotFoundException e) {
            cook = new Cook(cookId);
        }
        return cook;
    }
}

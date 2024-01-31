package ca.ulaval.glo4003.repul.kitchen.domain.cook;

import ca.ulaval.glo4003.repul.kitchen.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.exception.MealNotAssignedException;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.exception.MealNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Cook {

    private final CookId cookId;
    private final List<Meal> assignedMeals = new ArrayList<>();

    public Cook(CookId cookId) {
        this.cookId = cookId;
    }

    public List<Meal> getAssignedMeals() {
        return assignedMeals;
    }

    public CookId getCookId() {
        return cookId;
    }

    public List<MealId> assignMeals(MealFinder mealFinder, List<MealId> mealIds) {
        List<MealId> successfullyAssignedMealIds = new ArrayList<>();

        mealIds.forEach(mealId -> {
            try {
                Meal meal = mealFinder.findById(mealId);
                if (!assignedMeals.contains(meal)) {
                    assignedMeals.add(meal);
                    successfullyAssignedMealIds.add(mealId);
                }
            } catch (MealNotFoundException ignored) {}
        });

        return successfullyAssignedMealIds;
    }

    public List<MealId> unassignMeals(List<MealId> mealIds) {
        List<MealId> successfullyUnassignedMealIds = new ArrayList<>();

        mealIds.forEach(mealId -> {
            try {
                Meal assignedMeal = findAssignedMeal(mealId);
                assignedMeal.disassemble();
                assignedMeals.remove(assignedMeal);
                successfullyUnassignedMealIds.add(mealId);
            } catch (MealNotAssignedException ignored) {}
        });

        return successfullyUnassignedMealIds;
    }

    public List<MealId> assembleMeals(
        List<MealId> mealIds,
        ShipmentClient shipmentClient
    ) {
        List<MealId> assembleMealIds = new ArrayList<>();

        mealIds.forEach(mealId -> {
            try {
                Meal assignedMeal = findAssignedMeal(mealId);
                if (!assignedMeal.isAssembled()) {
                    assignedMeal.assemble();
                    assembleMealIds.add(mealId);
                }
            } catch (MealNotAssignedException ignored) {}
        });
        shipmentClient.addAssembledShipmentItem(assembleMealIds);

        return assembleMealIds;
    }

    private Meal findAssignedMeal(MealId mealId) {
        return assignedMeals
            .stream()
            .filter(meal -> meal.getMealId().equals(mealId))
            .findFirst()
            .orElseThrow(() -> new MealNotAssignedException(mealId));
    }
}

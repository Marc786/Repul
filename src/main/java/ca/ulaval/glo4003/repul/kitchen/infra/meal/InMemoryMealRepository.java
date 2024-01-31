package ca.ulaval.glo4003.repul.kitchen.infra.meal;

import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealRepository;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.cloner.MealFastCloner;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.exception.MealNotFoundException;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class InMemoryMealRepository implements MealRepository {

    private final Cloner cloner = new Cloner();
    private final List<Meal> meals = new ArrayList<>();

    public InMemoryMealRepository() {
        cloner.registerFastCloner(Meal.class, new MealFastCloner());
    }

    @Override
    public void save(Meal meal) {
        removeIfExisting(meal);
        meals.add(meal);
    }

    @Override
    public Meal findById(MealId mealId) {
        Meal mealFound = meals
            .stream()
            .filter(meal -> meal.getMealId().equals(mealId))
            .findFirst()
            .orElseThrow(() -> new MealNotFoundException(mealId));
        return cloner.deepClone(mealFound);
    }

    @Override
    public List<Meal> findAll() {
        return cloner.deepClone(meals);
    }

    private void removeIfExisting(Meal meal) {
        meals.removeIf(existingMeal -> existingMeal.getMealId().equals(meal.getMealId()));
    }
}

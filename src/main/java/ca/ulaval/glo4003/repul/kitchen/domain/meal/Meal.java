package ca.ulaval.glo4003.repul.kitchen.domain.meal;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Meal {

    private final MealId mealId;
    private final LocalDate deliveryDate;
    private final List<Recipe> recipes;
    private MealPreparationStatus mealPreparationStatus;

    public Meal(MealId mealId, LocalDate deliveryDate, List<Recipe> recipes) {
        this.mealId = mealId;
        this.deliveryDate = deliveryDate;
        this.recipes = recipes;
        this.mealPreparationStatus = MealPreparationStatus.ORDER_RECEIVED;
    }

    public Meal(
        MealId mealId,
        LocalDate deliveryDate,
        List<Recipe> recipes,
        MealPreparationStatus mealPreparationStatus
    ) {
        this.mealId = mealId;
        this.deliveryDate = deliveryDate;
        this.recipes = recipes;
        this.mealPreparationStatus = mealPreparationStatus;
    }

    public void assemble() {
        mealPreparationStatus = MealPreparationStatus.ASSEMBLED;
    }

    public boolean isAssembled() {
        return mealPreparationStatus == MealPreparationStatus.ASSEMBLED;
    }

    public MealId getMealId() {
        return mealId;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public MealPreparationStatus getMealPreparationStatus() {
        return mealPreparationStatus;
    }

    public void disassemble() {
        mealPreparationStatus = MealPreparationStatus.ORDER_RECEIVED;
    }

    public List<Ingredient> getIngredients() {
        return recipes.stream().flatMap(recipe -> recipe.ingredients().stream()).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Meal meal = (Meal) o;
        return (
            Objects.equals(mealId, meal.mealId) &&
            Objects.equals(deliveryDate, meal.deliveryDate) &&
            Objects.equals(recipes, meal.recipes) &&
            mealPreparationStatus == meal.mealPreparationStatus
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, deliveryDate, recipes, mealPreparationStatus);
    }
}

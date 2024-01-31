package ca.ulaval.glo4003.repul.kitchen.infra.meal.cloner;

import ca.ulaval.glo4003.lib.catalog.recipe.Ingredient;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.subscription.infra.cloner.LocalDateFastCloner;
import com.rits.cloning.Cloner;
import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealFastCloner implements IFastCloner {

    private final Cloner deepCloner = new Cloner();

    public MealFastCloner() {
        deepCloner.registerFastCloner(LocalDate.class, new LocalDateFastCloner());
    }

    @Override
    public Object clone(Object o, IDeepCloner iDeepCloner, Map<Object, Object> map) {
        final Meal original = (Meal) o;
        List<Recipe> copyRecipes = cloneRecipes(original.getRecipes());

        return new Meal(
            new MealId(original.getMealId().toString()),
            deepCloner.deepClone(original.getDeliveryDate()),
            copyRecipes,
            original.getMealPreparationStatus()
        );
    }

    private List<Recipe> cloneRecipes(List<Recipe> recipes) {
        return recipes
            .stream()
            .map(recipe ->
                new Recipe(
                    recipe.name(),
                    recipe.calories(),
                    recipe
                        .ingredients()
                        .stream()
                        .map(ingredient ->
                            new Ingredient(
                                ingredient.name(),
                                ingredient.quantity(),
                                ingredient.unit()
                            )
                        )
                        .collect(Collectors.toList())
                )
            )
            .collect(Collectors.toList());
    }
}

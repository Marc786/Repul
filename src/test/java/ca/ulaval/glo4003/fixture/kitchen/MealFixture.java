package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.lib.catalog.MealKitCatalog;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.finder.MealRecipeFinder;
import ca.ulaval.glo4003.lib.catalog.recipe.Recipe;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MealFixture {

    private static final MealRecipeFinder mealRecipeFinder = new MealKitCatalog();
    private final List<Recipe> recipes = mealRecipeFinder.findRecipes(
        MealKitType.STANDARD
    );
    private final String meal_id_string = UUID.randomUUID().toString();
    private MealId mealId = new MealId(meal_id_string);
    private final String deliveryDateString = "2021-09-01";
    private LocalDate deliveryDate = LocalDate.parse(deliveryDateString);

    public Meal build() {
        return new Meal(mealId, deliveryDate, recipes);
    }

    public MealFixture withMealId(MealId mealId) {
        this.mealId = mealId;
        return this;
    }

    public MealFixture withDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }
}

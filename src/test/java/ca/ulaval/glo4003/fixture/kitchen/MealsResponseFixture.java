package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealsResponse;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import java.util.List;

public class MealsResponseFixture {

    private MealResponse mealResponse = new MealResponseFixture().build();

    public MealsResponse build() {
        return new MealsResponse(List.of(mealResponse));
    }

    public MealsResponseFixture withMealResponse(MealResponse mealResponse) {
        this.mealResponse = mealResponse;
        return this;
    }

    public MealsResponseFixture withMeal(Meal meal) {
        MealResponse mealResponse = new MealResponseFixture().withMeal(meal).build();
        return this.withMealResponse(mealResponse);
    }
}

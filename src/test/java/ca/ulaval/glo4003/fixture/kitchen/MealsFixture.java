package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meals;
import java.util.List;

public class MealsFixture {

    private final Meal meal = new MealFixture().build();

    public Meals build() {
        return new Meals(List.of(meal));
    }
}

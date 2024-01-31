package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.request.MealIdsRequest;
import java.util.ArrayList;
import java.util.List;

public class MealIdsRequestFixture {

    private List<String> mealIds = new ArrayList<>();

    public MealIdsRequest build() {
        return new MealIdsRequest(mealIds);
    }

    public MealIdsRequestFixture withMealIds(List<String> mealIds) {
        this.mealIds = mealIds;
        return this;
    }
}

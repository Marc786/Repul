package ca.ulaval.glo4003.repul.kitchen.api.cook.assembler;

import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.request.MealIdsRequest;
import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.response.MealIdsResponse;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import java.util.List;
import java.util.Objects;

public class MealIdAssembler {

    public MealIdsResponse toMealIdsResponse(List<MealId> mealIds) {
        return new MealIdsResponse(mealIds.stream().map(MealId::toString).toList());
    }

    public List<MealId> toMealIds(MealIdsRequest mealIdsRequest) {
        return mealIdsRequest
            .mealIds()
            .stream()
            .map(this::createMealIdSafely)
            .filter(Objects::nonNull)
            .toList();
    }

    private MealId createMealIdSafely(String mealId) {
        try {
            return new MealId(mealId);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}

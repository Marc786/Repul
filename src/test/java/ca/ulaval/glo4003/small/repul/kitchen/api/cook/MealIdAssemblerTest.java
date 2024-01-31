package ca.ulaval.glo4003.small.repul.kitchen.api.cook;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.repul.kitchen.api.cook.assembler.MealIdAssembler;
import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.request.MealIdsRequest;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealIdAssemblerTest {

    private static final String MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final String INVALID_MEAL_ID_STRING = "invalid";
    private static final List<String> MEAL_IDS_STRING = List.of(MEAL_ID_STRING);
    private static final MealIdsRequest MEAL_IDS_REQUEST = new MealIdsRequest(
        MEAL_IDS_STRING
    );
    private static final MealId MEAL_ID = new MealId(MEAL_ID_STRING);
    private static final List<MealId> MEAL_IDS = List.of(MEAL_ID);
    private MealIdAssembler mealIdAssembler;

    @BeforeEach
    void setup() {
        mealIdAssembler = new MealIdAssembler();
    }

    @Test
    void toMealIds_shouldReturnMealIds() {
        List<MealId> actualMealIds = mealIdAssembler.toMealIds(MEAL_IDS_REQUEST);

        assertEquals(MEAL_IDS, actualMealIds);
    }

    @Test
    void validAndInvalidMealIds_returnsOnlyValidMealIds() {
        List<String> mealIdsString = List.of(MEAL_ID_STRING, INVALID_MEAL_ID_STRING);
        MealIdsRequest mealIdsRequest = new MealIdsRequest(mealIdsString);

        List<MealId> actualMealIds = mealIdAssembler.toMealIds(mealIdsRequest);

        assertEquals(MEAL_IDS, actualMealIds);
    }
}

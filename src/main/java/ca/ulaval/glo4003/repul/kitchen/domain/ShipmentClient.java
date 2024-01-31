package ca.ulaval.glo4003.repul.kitchen.domain;

import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import java.util.List;

public interface ShipmentClient {
    void addAssembledShipmentItem(List<MealId> mealId);
}

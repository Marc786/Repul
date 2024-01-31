package ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit;

import java.util.Objects;
import java.util.UUID;

public class MealKitId {

    private final UUID value;

    public MealKitId() {
        this.value = UUID.randomUUID();
    }

    public MealKitId(String value) {
        this.value = UUID.fromString(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MealKitId mealKitId = (MealKitId) obj;

        return value.equals(mealKitId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

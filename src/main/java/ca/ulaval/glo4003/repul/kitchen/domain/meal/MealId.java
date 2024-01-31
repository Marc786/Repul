package ca.ulaval.glo4003.repul.kitchen.domain.meal;

import java.util.Objects;
import java.util.UUID;

public class MealId {

    private final UUID value;

    public MealId(String value) {
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

        MealId mealId = (MealId) obj;

        return value.equals(mealId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

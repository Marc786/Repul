package ca.ulaval.glo4003.repul.kitchen.domain.cook;

import java.util.Objects;
import java.util.UUID;

public class CookId {

    private final UUID value;

    public CookId(String value) {
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

        CookId cookId = (CookId) obj;

        return value.equals(cookId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

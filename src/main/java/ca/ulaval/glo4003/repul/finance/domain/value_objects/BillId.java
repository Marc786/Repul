package ca.ulaval.glo4003.repul.finance.domain.value_objects;

import java.util.Objects;
import java.util.UUID;

public class BillId {

    private final UUID value;

    public BillId() {
        this.value = UUID.randomUUID();
    }

    public BillId(String value) {
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

        BillId billId = (BillId) obj;

        return value.equals(billId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

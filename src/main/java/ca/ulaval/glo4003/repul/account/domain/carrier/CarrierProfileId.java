package ca.ulaval.glo4003.repul.account.domain.carrier;

import ca.ulaval.glo4003.lib.value_object.AccountId;
import java.util.Objects;
import java.util.UUID;

public class CarrierProfileId implements AccountId {

    private final UUID value;

    public CarrierProfileId() {
        this.value = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        CarrierProfileId that = (CarrierProfileId) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

package ca.ulaval.glo4003.repul.account.domain.carrier;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import java.util.Objects;

public class CarrierProfile {

    private final CarrierProfileId id;
    private final EmailAddress emailAddress;
    private final Name name;

    public CarrierProfile(CarrierProfileId id, EmailAddress emailAddress, Name name) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.name = name;
    }

    public CarrierProfileId getId() {
        return id;
    }

    public EmailAddress getEmail() {
        return emailAddress;
    }

    public Name getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        CarrierProfile that = (CarrierProfile) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

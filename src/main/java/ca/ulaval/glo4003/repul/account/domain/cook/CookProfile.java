package ca.ulaval.glo4003.repul.account.domain.cook;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import java.util.Objects;

public class CookProfile {

    private final CookProfileId id;
    private final Name name;
    private final EmailAddress email;

    public CookProfile(CookProfileId id, Name name, EmailAddress email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public CookProfileId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public EmailAddress getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (CookProfile) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

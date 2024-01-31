package ca.ulaval.glo4003.repul.auth.infra.credential.admin;

import ca.ulaval.glo4003.lib.value_object.AccountId;
import java.util.UUID;

public class AdminId implements AccountId {

    private final UUID value;

    public AdminId(String value) {
        this.value = UUID.fromString(value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}

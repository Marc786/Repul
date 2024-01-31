package ca.ulaval.glo4003.repul.auth.domain.credential;

import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidRoleException;
import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER"),
    COOK("COOK"),
    CARRIER("CARRIER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public static Role fromString(String input) {
        Optional<Role> matchingRole = Arrays
            .stream(values())
            .filter(role -> role.value.equalsIgnoreCase(input))
            .findFirst();

        return matchingRole.orElseThrow(InvalidRoleException::new);
    }
}

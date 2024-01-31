package ca.ulaval.glo4003.small.repul.auth.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidRoleException;
import org.junit.jupiter.api.Test;

class RoleTest {

    private static final String INVALID_ROLE = "role";
    private static final String VALID_ROLE = "CUSTOMER";

    @Test
    void invalidRole_fromString_throwsInvalidRoleException() {
        assertThrows(InvalidRoleException.class, () -> Role.fromString(INVALID_ROLE));
    }

    @Test
    void validRole_fromString_doesNotThrow() {
        Role expectedRole = Role.CUSTOMER;

        Role actualRole = Role.fromString(VALID_ROLE);

        assertEquals(expectedRole, actualRole);
    }
}

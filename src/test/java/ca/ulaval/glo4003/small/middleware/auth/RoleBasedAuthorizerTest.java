package ca.ulaval.glo4003.small.middleware.auth;

import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.fixture.auth.TokenPayloadFixture;
import ca.ulaval.glo4003.middleware.auth.RoleBasedAuthorizer;
import ca.ulaval.glo4003.middleware.auth.exception.ForbiddenException;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleBasedAuthorizerTest {

    private static final Role[] allowedRoles = new Role[] { Role.CUSTOMER };
    private final TokenPayloadFixture tokenPayloadFixture = new TokenPayloadFixture();
    private RoleBasedAuthorizer roleBasedAuthorizer;

    @BeforeEach
    void setup() {
        roleBasedAuthorizer = new RoleBasedAuthorizer();
    }

    @Test
    void roleIsInAllowedRoles_authorize_doesNotThrow() {
        TokenPayload tokenPayload = tokenPayloadFixture.withRole(Role.CUSTOMER).build();

        roleBasedAuthorizer.authorize(tokenPayload, allowedRoles);
    }

    @Test
    void roleIsNotInAllowedRoles_authorize_throwsForbiddenException() {
        TokenPayload tokenPayload = tokenPayloadFixture.withRole(Role.ADMIN).build();

        assertThrows(
            ForbiddenException.class,
            () -> roleBasedAuthorizer.authorize(tokenPayload, allowedRoles)
        );
    }
}

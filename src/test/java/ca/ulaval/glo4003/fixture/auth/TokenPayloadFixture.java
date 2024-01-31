package ca.ulaval.glo4003.fixture.auth;

import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenPayload;
import java.util.Date;
import java.util.UUID;

public class TokenPayloadFixture {

    private String id = UUID.randomUUID().toString();
    private final Date expirationDate = new Date();
    private Role role = Role.COOK;

    public TokenPayload build() {
        return new TokenPayload(id, expirationDate, role);
    }

    public TokenPayloadFixture withId(String id) {
        this.id = id;
        return this;
    }

    public TokenPayloadFixture withRole(Role role) {
        this.role = role;
        return this;
    }
}

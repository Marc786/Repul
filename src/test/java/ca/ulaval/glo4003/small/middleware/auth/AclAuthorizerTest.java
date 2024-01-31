package ca.ulaval.glo4003.small.middleware.auth;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.fixture.auth.TokenPayloadFixture;
import ca.ulaval.glo4003.middleware.auth.AclAuthorizer;
import ca.ulaval.glo4003.middleware.auth.exception.ForbiddenException;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AclAuthorizerTest {

    private static final String ACCOUNT_ID = "accountId";
    private static final String ANOTHER_ACCOUNT_ID = "anotherAccountId";

    private final TokenPayloadFixture tokenPayloadFixture = new TokenPayloadFixture();
    private AclAuthorizer aclAuthorizer;

    @BeforeEach
    void setup() {
        aclAuthorizer = new AclAuthorizer();
    }

    @Test
    void accountIdAndTokenIdMatch_authorize_doesNotThrow() {
        TokenPayload tokenPayload = tokenPayloadFixture.withId(ACCOUNT_ID).build();

        assertDoesNotThrow(() -> aclAuthorizer.authorize(tokenPayload, ACCOUNT_ID));
    }

    @Test
    void accountIdAndTokenIdDoNotMatch_authorize_throwsForbiddenException() {
        TokenPayload tokenPayload = tokenPayloadFixture
            .withId(ANOTHER_ACCOUNT_ID)
            .build();

        assertThrows(
            ForbiddenException.class,
            () -> aclAuthorizer.authorize(tokenPayload, ACCOUNT_ID)
        );
    }
}

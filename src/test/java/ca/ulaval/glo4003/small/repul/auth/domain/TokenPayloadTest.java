package ca.ulaval.glo4003.small.repul.auth.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.constant.Constants;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidTokenException;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenPayload;
import java.util.Date;
import org.junit.jupiter.api.Test;

class TokenPayloadTest {

    private static final String ID = "id";
    private static final Date VALID_EXPIRATION_DATE = new Date(
        System.currentTimeMillis() + Constants.Auth.EXPIRATION_DELAY_IN_MS
    );
    private static final Date INVALID_EXPIRATION_DATE = new Date(
        System.currentTimeMillis() - 1000
    );
    private static final Role ROLE = Role.CUSTOMER;
    private TokenPayload tokenPayload;

    @Test
    void tokenNotExpired_verifyExpiration_doesNotThrow() {
        tokenPayload = new TokenPayload(ID, VALID_EXPIRATION_DATE, ROLE);

        assertDoesNotThrow(() -> tokenPayload.verifyExpiration());
    }

    @Test
    void tokenExpired_verifyExpiration_throwsInvalidTokenException() {
        tokenPayload = new TokenPayload(ID, INVALID_EXPIRATION_DATE, ROLE);

        assertThrows(InvalidTokenException.class, () -> tokenPayload.verifyExpiration());
    }
}

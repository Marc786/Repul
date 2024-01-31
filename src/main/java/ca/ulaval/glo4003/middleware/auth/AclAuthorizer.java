package ca.ulaval.glo4003.middleware.auth;

import ca.ulaval.glo4003.middleware.auth.exception.ForbiddenException;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenPayload;
import java.util.Objects;

public class AclAuthorizer {

    public void authorize(TokenPayload tokenPayload, String accountId) {
        if (!accountIdAndTokenIdMatches(tokenPayload, accountId)) {
            throw new ForbiddenException();
        }
    }

    private boolean accountIdAndTokenIdMatches(
        TokenPayload tokenPayload,
        String accountId
    ) {
        return Objects.equals(tokenPayload.getId(), accountId);
    }
}

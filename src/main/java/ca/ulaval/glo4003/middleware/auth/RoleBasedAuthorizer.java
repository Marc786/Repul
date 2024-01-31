package ca.ulaval.glo4003.middleware.auth;

import ca.ulaval.glo4003.middleware.auth.exception.ForbiddenException;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenPayload;
import java.util.List;

public class RoleBasedAuthorizer {

    public void authorize(TokenPayload tokenPayload, Role[] allowedRoles) {
        if (!List.of(allowedRoles).contains(tokenPayload.getRole())) {
            throw new ForbiddenException();
        }
    }
}

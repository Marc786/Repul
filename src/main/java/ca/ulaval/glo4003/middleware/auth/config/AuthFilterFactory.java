package ca.ulaval.glo4003.middleware.auth.config;

import ca.ulaval.glo4003.middleware.auth.AclAuthorizer;
import ca.ulaval.glo4003.middleware.auth.AuthFilter;
import ca.ulaval.glo4003.middleware.auth.RoleBasedAuthorizer;
import ca.ulaval.glo4003.repul.auth.infra.token.JWTAuth;
import jakarta.ws.rs.container.ContainerRequestFilter;

public class AuthFilterFactory {

    private final RoleBasedAuthorizer roleBasedAuthorizer;

    public AuthFilterFactory() {
        this.roleBasedAuthorizer = new RoleBasedAuthorizer();
    }

    public ContainerRequestFilter create() {
        return new AuthFilter(new JWTAuth(), new AclAuthorizer(), roleBasedAuthorizer);
    }
}

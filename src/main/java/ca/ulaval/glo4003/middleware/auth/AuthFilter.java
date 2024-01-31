package ca.ulaval.glo4003.middleware.auth;

import static ca.ulaval.glo4003.constant.Constants.Auth.AUTHORIZATION_HEADER_NAME;
import static ca.ulaval.glo4003.constant.Constants.Auth.BEARER_PREFIX;

import ca.ulaval.glo4003.middleware.auth.annotation.ACL;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.middleware.auth.exception.TokenNotFoundException;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenPayload;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthFilter implements ContainerRequestFilter {

    private final TokenAuth tokenAuth;
    private final AclAuthorizer aclAuthorizer;
    private final RoleBasedAuthorizer roleBasedAuthorizer;

    @Context
    private ResourceInfo resourceInfo;

    public AuthFilter(
        TokenAuth tokenAuth,
        AclAuthorizer aclAuthorizer,
        RoleBasedAuthorizer roleBasedAuthorizer
    ) {
        this.tokenAuth = tokenAuth;
        this.aclAuthorizer = aclAuthorizer;
        this.roleBasedAuthorizer = roleBasedAuthorizer;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        boolean rbacAnnotationPresent = resourceInfo
            .getResourceMethod()
            .isAnnotationPresent(RBAC.class);
        boolean aclAnnotationPresent = resourceInfo
            .getResourceMethod()
            .isAnnotationPresent(ACL.class);

        if (rbacAnnotationPresent || aclAnnotationPresent) {
            TokenPayload tokenPayload = authenticate(requestContext);

            if (rbacAnnotationPresent) {
                authorizeByRole(tokenPayload);
            }

            if (aclAnnotationPresent) {
                authorizeBySubject(requestContext, tokenPayload);
            }
        }
    }

    private TokenPayload authenticate(ContainerRequestContext requestContext) {
        String token = getToken(requestContext);
        TokenPayload tokenPayload = tokenAuth.decode(token);
        tokenPayload.verifyExpiration();
        return tokenPayload;
    }

    private void authorizeByRole(TokenPayload tokenPayload) {
        Role[] roles = resourceInfo.getResourceMethod().getAnnotation(RBAC.class).roles();

        roleBasedAuthorizer.authorize(tokenPayload, roles);
    }

    private void authorizeBySubject(
        ContainerRequestContext requestContext,
        TokenPayload tokenPayload
    ) {
        String accountIdentifier = resourceInfo
            .getResourceMethod()
            .getAnnotation(ACL.class)
            .accountId();

        String accountIdentifierValue = requestContext
            .getUriInfo()
            .getPathParameters()
            .getFirst(accountIdentifier);

        aclAuthorizer.authorize(tokenPayload, accountIdentifierValue);
    }

    private String getToken(ContainerRequestContext requestContext) {
        String auth = requestContext.getHeaders().getFirst(AUTHORIZATION_HEADER_NAME);
        if (auth == null) {
            throw new TokenNotFoundException();
        }

        return auth.substring(BEARER_PREFIX.length()).trim();
    }
}

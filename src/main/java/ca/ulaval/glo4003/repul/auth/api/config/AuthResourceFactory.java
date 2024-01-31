package ca.ulaval.glo4003.repul.auth.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.repul.auth.api.AuthResource;
import ca.ulaval.glo4003.repul.auth.application.AuthService;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import ca.ulaval.glo4003.repul.auth.infra.token.JWTAuth;

public class AuthResourceFactory {

    private final AuthService authService;
    private final TokenAuth tokenAuth;

    public AuthResourceFactory() {
        ServiceLocator instance = ServiceLocator.getInstance();
        CredentialRepository credentialRepository = instance.getService(
            CredentialRepository.class
        );

        this.authService = new AuthService(credentialRepository);
        this.tokenAuth = new JWTAuth();
    }

    public AuthResource create() {
        return new AuthResource(authService, tokenAuth);
    }
}

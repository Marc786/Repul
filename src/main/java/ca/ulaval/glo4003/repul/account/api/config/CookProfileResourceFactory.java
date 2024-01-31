package ca.ulaval.glo4003.repul.account.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.repul.account.api.cook.CookProfileResource;
import ca.ulaval.glo4003.repul.account.application.cook.CookProfileService;
import ca.ulaval.glo4003.repul.account.application.cook.CreateCookProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileRepository;
import ca.ulaval.glo4003.repul.auth.application.AuthService;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;

public class CookProfileResourceFactory {

    private final CookProfileService cookProfileService;

    public CookProfileResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        CredentialRepository credentialRepository = serviceLocator.getService(
            CredentialRepository.class
        );
        CookProfileRepository cookProfileRepository = serviceLocator.getService(
            CookProfileRepository.class
        );
        AuthService authService = new AuthService(credentialRepository);
        CreateCookProfileObservable createCookProfileObservable =
            new CreateCookProfileObservable();
        createCookProfileObservable.register(authService);
        this.cookProfileService =
            new CookProfileService(cookProfileRepository, createCookProfileObservable);
    }

    public CookProfileResource create() {
        return new CookProfileResource(cookProfileService);
    }
}

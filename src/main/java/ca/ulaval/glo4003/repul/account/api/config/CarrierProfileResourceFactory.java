package ca.ulaval.glo4003.repul.account.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.repul.account.api.carrier.CarrierProfileResource;
import ca.ulaval.glo4003.repul.account.application.carrier.CarrierProfileService;
import ca.ulaval.glo4003.repul.account.application.carrier.CreateCarrierProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileRepository;
import ca.ulaval.glo4003.repul.auth.application.AuthService;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;

public class CarrierProfileResourceFactory {

    private final CarrierProfileService carrierProfileService;

    public CarrierProfileResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        CredentialRepository credentialRepository = serviceLocator.getService(
            CredentialRepository.class
        );
        CarrierProfileRepository carrierProfileRepository = serviceLocator.getService(
            CarrierProfileRepository.class
        );
        AuthService authService = new AuthService(credentialRepository);
        CreateCarrierProfileObservable createCarrierProfileObservable =
            new CreateCarrierProfileObservable();
        createCarrierProfileObservable.register(authService);
        this.carrierProfileService =
            new CarrierProfileService(
                carrierProfileRepository,
                createCarrierProfileObservable
            );
    }

    public CarrierProfileResource create() {
        return new CarrierProfileResource(carrierProfileService);
    }
}

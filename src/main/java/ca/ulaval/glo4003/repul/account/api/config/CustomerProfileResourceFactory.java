package ca.ulaval.glo4003.repul.account.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.repul.account.api.customer.CustomerProfileResource;
import ca.ulaval.glo4003.repul.account.api.customer.assembler.CustomerProfileAssembler;
import ca.ulaval.glo4003.repul.account.application.customer.CreateCustomerProfileObservable;
import ca.ulaval.glo4003.repul.account.application.customer.CustomerProfileService;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileFactory;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileRepository;
import ca.ulaval.glo4003.repul.auth.application.AuthService;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;

public class CustomerProfileResourceFactory {

    private final CustomerProfileService customerProfileService;
    private final CustomerProfileAssembler customerAssembler;

    public CustomerProfileResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        CustomerProfileRepository customerProfileRepository = serviceLocator.getService(
            CustomerProfileRepository.class
        );
        CredentialRepository credentialRepository = serviceLocator.getService(
            CredentialRepository.class
        );
        AuthService authService = new AuthService(credentialRepository);
        CustomerProfileFactory customerProfileFactory = new CustomerProfileFactory();

        CreateCustomerProfileObservable createCustomerProfileObservable =
            new CreateCustomerProfileObservable();
        createCustomerProfileObservable.register(authService);

        this.customerProfileService =
            new CustomerProfileService(
                customerProfileFactory,
                customerProfileRepository,
                createCustomerProfileObservable
            );
        this.customerAssembler = new CustomerProfileAssembler();
    }

    public CustomerProfileResource create() {
        return new CustomerProfileResource(customerProfileService, customerAssembler);
    }
}

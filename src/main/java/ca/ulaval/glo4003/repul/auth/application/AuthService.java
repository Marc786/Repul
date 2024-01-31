package ca.ulaval.glo4003.repul.auth.application;

import ca.ulaval.glo4003.event.account.carrier.CarrierCreatedEvent;
import ca.ulaval.glo4003.event.account.carrier.CreateCarrierProfileObserver;
import ca.ulaval.glo4003.event.account.cook.CookCreatedEvent;
import ca.ulaval.glo4003.event.account.cook.CreateCookProfileObserver;
import ca.ulaval.glo4003.event.account.customer.CreateCustomerProfileObserver;
import ca.ulaval.glo4003.event.account.customer.CustomerCreatedEvent;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;

public class AuthService
    implements
        CreateCustomerProfileObserver,
        CreateCookProfileObserver,
        CreateCarrierProfileObserver {

    private final CredentialRepository credentialRepository;

    public AuthService(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public Credential login(EmailAddress email, Password password, Role role) {
        Credential credential = credentialRepository.findUserCredential(email, role);
        credential.verifyPassword(password);
        return credential;
    }

    @Override
    public void listenCookCreated(CookCreatedEvent cookCreatedEvent) {
        credentialRepository.save(
            new Credential(
                new CredentialId(cookCreatedEvent.accountId()),
                cookCreatedEvent.email(),
                cookCreatedEvent.password(),
                Role.COOK
            )
        );
    }

    @Override
    public void listenCustomerCreated(CustomerCreatedEvent customerCreatedEvent) {
        credentialRepository.save(
            new Credential(
                new CredentialId(customerCreatedEvent.accountId()),
                customerCreatedEvent.email(),
                customerCreatedEvent.password(),
                Role.CUSTOMER
            )
        );
    }

    @Override
    public void listenCarrierCreated(CarrierCreatedEvent carrierCreatedEvent) {
        credentialRepository.save(
            new Credential(
                new CredentialId(carrierCreatedEvent.accountId()),
                carrierCreatedEvent.email(),
                carrierCreatedEvent.password(),
                Role.CARRIER
            )
        );
    }
}

package ca.ulaval.glo4003.medium.auth.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.event.account.carrier.CarrierCreatedEvent;
import ca.ulaval.glo4003.event.account.cook.CookCreatedEvent;
import ca.ulaval.glo4003.event.account.customer.CustomerCreatedEvent;
import ca.ulaval.glo4003.lib.value_object.AccountId;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.auth.application.AuthService;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.repul.auth.infra.credential.InMemoryCredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    private static final AccountId ACCOUNT_ID = new CustomerProfileId("123");
    private static final EmailAddress EMAIL_ADDRESS = new EmailAddress(
        "david.ferland@ulaval.ca"
    );
    private static final Password PASSWORD = new Password("password");

    private CredentialRepository credentialRepository;
    private AuthService authService;

    @BeforeEach
    void setup() {
        credentialRepository = new InMemoryCredentialRepository();
        authService = new AuthService(credentialRepository);
    }

    @Test
    void listenCustomerCreated_saveCredentialInRepository() {
        authService.listenCustomerCreated(
            new CustomerCreatedEvent(ACCOUNT_ID, EMAIL_ADDRESS, PASSWORD)
        );

        Credential credential = credentialRepository.findUserCredential(
            EMAIL_ADDRESS,
            Role.CUSTOMER
        );
        assertEquals(EMAIL_ADDRESS, credential.getEmail());
        assertEquals(PASSWORD, credential.getPassword());
        assertEquals(Role.CUSTOMER, credential.getRole());
    }

    @Test
    void listenCarrierCreated_saveCredentialInRepository() {
        authService.listenCarrierCreated(
            new CarrierCreatedEvent(ACCOUNT_ID, EMAIL_ADDRESS, PASSWORD)
        );

        Credential credential = credentialRepository.findUserCredential(
            EMAIL_ADDRESS,
            Role.CARRIER
        );
        assertEquals(EMAIL_ADDRESS, credential.getEmail());
        assertEquals(PASSWORD, credential.getPassword());
        assertEquals(Role.CARRIER, credential.getRole());
    }

    @Test
    void listenCookCreated_credentialSavedInRepository() {
        authService.listenCookCreated(
            new CookCreatedEvent(ACCOUNT_ID, EMAIL_ADDRESS, PASSWORD)
        );

        Credential credential = credentialRepository.findUserCredential(
            EMAIL_ADDRESS,
            Role.COOK
        );
        assertEquals(EMAIL_ADDRESS, credential.getEmail());
        assertEquals(PASSWORD, credential.getPassword());
        assertEquals(Role.COOK, credential.getRole());
    }

    @Test
    void credentialNotInRepository_login_throwsInvalidCredentialException() {
        assertThrows(
            InvalidCredentialsException.class,
            () -> {
                authService.login(EMAIL_ADDRESS, PASSWORD, Role.CUSTOMER);
            }
        );
    }

    @Test
    void credentialInRepository_login_returnCredential() {
        authService.listenCustomerCreated(
            new CustomerCreatedEvent(ACCOUNT_ID, EMAIL_ADDRESS, PASSWORD)
        );

        Credential credential = authService.login(EMAIL_ADDRESS, PASSWORD, Role.CUSTOMER);

        assertEquals(EMAIL_ADDRESS, credential.getEmail());
        assertEquals(PASSWORD, credential.getPassword());
        assertEquals(Role.CUSTOMER, credential.getRole());
    }

    @Test
    void invalidPassword_login_throwsInvalidCredentialException() {
        authService.listenCustomerCreated(
            new CustomerCreatedEvent(ACCOUNT_ID, EMAIL_ADDRESS, PASSWORD)
        );

        assertThrows(
            InvalidCredentialsException.class,
            () -> {
                authService.login(EMAIL_ADDRESS, new Password("invalid"), Role.CUSTOMER);
            }
        );
    }
}

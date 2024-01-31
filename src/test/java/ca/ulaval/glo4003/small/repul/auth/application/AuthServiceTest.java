package ca.ulaval.glo4003.small.repul.auth.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.event.account.carrier.CarrierCreatedEvent;
import ca.ulaval.glo4003.event.account.cook.CookCreatedEvent;
import ca.ulaval.glo4003.event.account.customer.CustomerCreatedEvent;
import ca.ulaval.glo4003.fixture.account.CarrierProfileFixture;
import ca.ulaval.glo4003.fixture.account.CookProfileFixture;
import ca.ulaval.glo4003.fixture.account.CustomerProfileFixture;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfile;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.auth.application.AuthService;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    private static final CustomerProfileId ID = new CustomerProfileId("id");
    private static final EmailAddress EMAIL = new EmailAddress("email");
    private static final Password PASSWORD = new Password("password");
    private static final Role ROLE = Role.CUSTOMER;
    private static final Credential CREDENTIAL = new Credential(
        new CredentialId(ID),
        EMAIL,
        PASSWORD,
        ROLE
    );

    private final CookProfileFixture cookProfileFixture = new CookProfileFixture();
    private final CustomerProfileFixture customerProfileFixture =
        new CustomerProfileFixture();
    private final CarrierProfileFixture carrierProfileFixture =
        new CarrierProfileFixture();
    private final CredentialRepository credentialRepository = mock(
        CredentialRepository.class
    );
    private AuthService authService;

    @BeforeEach
    void setup() {
        authService = new AuthService(credentialRepository);
    }

    @Test
    void login_returnsCredential() {
        when(credentialRepository.findUserCredential(EMAIL, ROLE)).thenReturn(CREDENTIAL);

        Credential actualCredential = authService.login(EMAIL, PASSWORD, ROLE);

        assertEquals(CREDENTIAL, actualCredential);
    }

    @Test
    void listenCookCreated() {
        CookProfile cookProfile = cookProfileFixture.build();
        Credential expectedCredential = new Credential(
            new CredentialId(cookProfile.getId()),
            cookProfile.getEmail(),
            PASSWORD,
            Role.COOK
        );
        CookCreatedEvent cookCreatedEvent = new CookCreatedEvent(
            cookProfile.getId(),
            cookProfile.getEmail(),
            PASSWORD
        );

        authService.listenCookCreated(cookCreatedEvent);

        verify(credentialRepository).save(expectedCredential);
    }

    @Test
    void listenCustomerCreated() {
        CustomerProfile customerProfile = customerProfileFixture.build();
        Credential expectedCredential = new Credential(
            new CredentialId(customerProfile.getId()),
            customerProfile.getEmail(),
            PASSWORD,
            Role.CUSTOMER
        );
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent(
            customerProfile.getId(),
            customerProfile.getEmail(),
            PASSWORD
        );

        authService.listenCustomerCreated(customerCreatedEvent);

        verify(credentialRepository).save(expectedCredential);
    }

    @Test
    void listenCarrierCreated() {
        CarrierProfile carrierProfile = carrierProfileFixture.build();
        Credential expectedCredential = new Credential(
            new CredentialId(carrierProfile.getId()),
            carrierProfile.getEmail(),
            PASSWORD,
            Role.CARRIER
        );
        CarrierCreatedEvent carrierCreatedEvent = new CarrierCreatedEvent(
            carrierProfile.getId(),
            carrierProfile.getEmail(),
            PASSWORD
        );

        authService.listenCarrierCreated(carrierCreatedEvent);

        verify(credentialRepository).save(expectedCredential);
    }
}

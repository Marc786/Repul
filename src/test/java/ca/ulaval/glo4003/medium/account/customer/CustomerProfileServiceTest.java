package ca.ulaval.glo4003.medium.account.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.fixture.account.CustomerProfileFixture;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.customer.CreateCustomerProfileObservable;
import ca.ulaval.glo4003.repul.account.application.customer.CustomerProfileService;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileFactory;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCustomerProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerProfileServiceTest {

    private static final Password PASSWORD = new Password("password");
    private final CustomerProfileFixture customerProfileFixture =
        new CustomerProfileFixture();
    private final CustomerProfile customerProfile = customerProfileFixture.build();
    private final CreateCustomerProfileObservable createCustomerProfileObservableMock =
        mock(CreateCustomerProfileObservable.class);
    private final CustomerProfileFactory customerProfileFactory =
        new CustomerProfileFactory();

    private CustomerProfileRepository customerProfileRepository;
    private CustomerProfileService customerProfileService;

    @BeforeEach
    void setup() {
        customerProfileRepository = new InMemoryCustomerProfileRepository();
        customerProfileService =
            new CustomerProfileService(
                customerProfileFactory,
                customerProfileRepository,
                createCustomerProfileObservableMock
            );
    }

    @Test
    void newCustomer_createCustomer_customerIsSaved() {
        CustomerProfileId customerProfileId = getIdulOfCreatedCustomer();
        CustomerProfile expectedCustomerProfile = customerProfileFixture
            .withId(customerProfileId)
            .build();
        CustomerProfile savedCustomerProfile = customerProfileRepository.findByIdul(
            customerProfileId
        );

        assertEquals(expectedCustomerProfile, savedCustomerProfile);
    }

    @Test
    void getCustomer_customerIsRetrieved() {
        CustomerProfileId customerProfileId = getIdulOfCreatedCustomer();
        CustomerProfile expectedCustomerProfile = customerProfileFixture
            .withId(customerProfileId)
            .build();

        CustomerProfile actualCustomerProfile = customerProfileService.getCustomer(
            customerProfileId
        );

        assertEquals(expectedCustomerProfile, actualCustomerProfile);
    }

    private CustomerProfileId getIdulOfCreatedCustomer() {
        return customerProfileService.createCustomer(
            customerProfile.getEmail(),
            PASSWORD,
            customerProfile.getName(),
            customerProfile.getBirthDate(),
            customerProfile.getGender(),
            customerProfile.getId(),
            customerProfile.getStudentCard()
        );
    }
}

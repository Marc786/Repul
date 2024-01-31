package ca.ulaval.glo4003.small.repul.account.application.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.event.account.customer.CustomerCreatedEvent;
import ca.ulaval.glo4003.fixture.account.CustomerProfileFixture;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.customer.CreateCustomerProfileObservable;
import ca.ulaval.glo4003.repul.account.application.customer.CustomerProfileService;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileFactory;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.CustomerProfileAlreadyExistsException;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.CustomerProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import org.junit.jupiter.api.Test;

class CustomerProfileServiceTest {

    private static final Password PASSWORD = new Password("password");
    private static final CustomerProfileId CUSTOMER_PROFILE_ID = new CustomerProfileId(
        "bmart007"
    );
    private static final StudentCard STUDENT_CARD = new StudentCard("123456789");
    private final CustomerProfileFixture customerProfileFixture =
        new CustomerProfileFixture();
    private final CustomerProfile customerProfile = customerProfileFixture
        .withId(CUSTOMER_PROFILE_ID)
        .withStudentCard(STUDENT_CARD)
        .build();

    private final CustomerProfileFactory customerProfileFactory =
        new CustomerProfileFactory();
    private final CreateCustomerProfileObservable createCustomerProfileObservableMock =
        mock(CreateCustomerProfileObservable.class);
    private final CustomerProfileRepository customerProfileRepositoryMock = mock(
        CustomerProfileRepository.class
    );

    private final CustomerProfileService customerProfileService =
        new CustomerProfileService(
            customerProfileFactory,
            customerProfileRepositoryMock,
            createCustomerProfileObservableMock
        );

    @Test
    void createCustomer_customerIsNotified() {
        when(customerProfileRepositoryMock.findByIdul(CUSTOMER_PROFILE_ID))
            .thenThrow(new CustomerProfileNotFoundException());
        when(customerProfileRepositoryMock.findByStudentCard(STUDENT_CARD))
            .thenThrow(new CustomerProfileNotFoundException());

        CustomerProfileId customerProfileId = createCustomer();

        CustomerCreatedEvent expectedCustomerCreatedEvent = new CustomerCreatedEvent(
            customerProfileId,
            customerProfile.getEmail(),
            PASSWORD
        );
        verify(createCustomerProfileObservableMock)
            .notifyCustomerCreated(expectedCustomerCreatedEvent);
    }

    @Test
    void existingCustomerWithSameCustomerId_createCustomer_throwsCustomerAlreadyExistsException() {
        when(customerProfileRepositoryMock.findByIdul(CUSTOMER_PROFILE_ID))
            .thenReturn(customerProfile);
        when(customerProfileRepositoryMock.findByStudentCard(STUDENT_CARD))
            .thenThrow(new CustomerProfileNotFoundException());

        assertThrows(CustomerProfileAlreadyExistsException.class, this::createCustomer);
    }

    @Test
    void existingCustomerWithSameCustomerId_createCustomer_notifyIsNotCalled() {
        when(customerProfileRepositoryMock.findByIdul(CUSTOMER_PROFILE_ID))
            .thenReturn(customerProfile);
        when(customerProfileRepositoryMock.findByStudentCard(STUDENT_CARD))
            .thenThrow(new CustomerProfileNotFoundException());

        CustomerCreatedEvent expectedCustomerCreatedEvent = new CustomerCreatedEvent(
            customerProfile.getId(),
            customerProfile.getEmail(),
            PASSWORD
        );
        verify(createCustomerProfileObservableMock, never())
            .notifyCustomerCreated(expectedCustomerCreatedEvent);
    }

    @Test
    void existingCustomerWithSameCustomerId_createCustomer_saveIsNotCalled() {
        when(customerProfileRepositoryMock.findByIdul(CUSTOMER_PROFILE_ID))
            .thenReturn(customerProfile);
        when(customerProfileRepositoryMock.findByStudentCard(STUDENT_CARD))
            .thenThrow(new CustomerProfileNotFoundException());

        verify(customerProfileRepositoryMock, never()).save(customerProfile);
    }

    @Test
    void existingCustomerWithSameStudentCard_createCustomer_throwsCustomerAlreadyExistsException() {
        when(customerProfileRepositoryMock.findByIdul(CUSTOMER_PROFILE_ID))
            .thenThrow(new CustomerProfileNotFoundException());
        when(customerProfileRepositoryMock.findByStudentCard(STUDENT_CARD))
            .thenReturn(customerProfile);

        assertThrows(CustomerProfileAlreadyExistsException.class, this::createCustomer);
    }

    @Test
    void existingCustomerWithSameStudentCard_createCustomer_notifyIsNotCalled() {
        when(customerProfileRepositoryMock.findByIdul(CUSTOMER_PROFILE_ID))
            .thenThrow(new CustomerProfileNotFoundException());
        when(customerProfileRepositoryMock.findByStudentCard(STUDENT_CARD))
            .thenReturn(customerProfile);

        CustomerCreatedEvent expectedCustomerCreatedEvent = new CustomerCreatedEvent(
            customerProfile.getId(),
            customerProfile.getEmail(),
            PASSWORD
        );
        verify(createCustomerProfileObservableMock, never())
            .notifyCustomerCreated(expectedCustomerCreatedEvent);
    }

    @Test
    void existingCustomerWithSameStudentCard_createCustomer_saveIsNotCalled() {
        when(customerProfileRepositoryMock.findByIdul(CUSTOMER_PROFILE_ID))
            .thenThrow(new CustomerProfileNotFoundException());
        when(customerProfileRepositoryMock.findByStudentCard(STUDENT_CARD))
            .thenReturn(customerProfile);

        verify(customerProfileRepositoryMock, never()).save(customerProfile);
    }

    private CustomerProfileId createCustomer() {
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

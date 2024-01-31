package ca.ulaval.glo4003.small.repul.account.infra;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.fixture.account.CustomerProfileFixture;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.CustomerProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCustomerProfileRepository;
import org.junit.jupiter.api.Test;

class InMemoryCustomerProfileRepositoryTest {

    private static final CustomerProfileId CUSTOMER_ID = new CustomerProfileId("idul123");
    private static final Name ANOTHER_NAME = new Name("anotherName", "anotherLastName");
    private final CustomerProfileFixture customerProfileFixture =
        new CustomerProfileFixture();
    private final CustomerProfile customerProfile = customerProfileFixture.build();

    private final CustomerProfileRepository customerProfileRepository =
        new InMemoryCustomerProfileRepository();

    @Test
    void findByIdul_deepCopyCustomerIsReturned() {
        CustomerProfile customerProfile = customerProfileFixture
            .withId(CUSTOMER_ID)
            .build();
        customerProfileRepository.save(customerProfile);

        CustomerProfile actualCustomerProfile = customerProfileRepository.findByIdul(
            CUSTOMER_ID
        );

        assertNotSame(customerProfile, actualCustomerProfile);
        assertEquals(customerProfile, actualCustomerProfile);
    }

    @Test
    void findByStudentCard_deepCopyCustomerIsReturned() {
        customerProfileRepository.save(customerProfile);

        CustomerProfile actualCustomerProfile =
            customerProfileRepository.findByStudentCard(customerProfile.getStudentCard());

        assertNotSame(customerProfile, actualCustomerProfile);
        assertEquals(customerProfile, actualCustomerProfile);
    }

    @Test
    void saveCustomer_customerIsSaved() {
        CustomerProfile customerProfile = customerProfileFixture
            .withId(CUSTOMER_ID)
            .build();
        customerProfileRepository.save(customerProfile);

        CustomerProfile actualCustomerProfile = customerProfileRepository.findByIdul(
            CUSTOMER_ID
        );
        assertEquals(customerProfile, actualCustomerProfile);
    }

    @Test
    void existingCustomer_saveCustomer_customerIsUpdated() {
        customerProfileRepository.save(customerProfile);
        CustomerProfile updatedCustomerProfile = customerProfileFixture
            .withUserName(ANOTHER_NAME)
            .build();

        customerProfileRepository.save(updatedCustomerProfile);

        CustomerProfile actualCustomerProfile = customerProfileRepository.findByIdul(
            updatedCustomerProfile.getId()
        );
        assertEquals(updatedCustomerProfile, actualCustomerProfile);
    }

    @Test
    void existingCustomer_findByIdul_customerIsReturned() {
        customerProfileRepository.save(customerProfile);

        CustomerProfile actualCustomerProfile = customerProfileRepository.findByIdul(
            customerProfile.getId()
        );

        assertEquals(customerProfile, actualCustomerProfile);
    }

    @Test
    void newCustomer_findByIdul_customerNotFoundExceptionIsThrown() {
        CustomerProfile customerProfile = customerProfileFixture
            .withId(CUSTOMER_ID)
            .build();

        assertThrows(
            CustomerProfileNotFoundException.class,
            () -> customerProfileRepository.findByIdul(customerProfile.getId())
        );
    }

    @Test
    void existingCustomer_findByStudentCard_customerIsReturned() {
        customerProfileRepository.save(customerProfile);

        CustomerProfile actualCustomerProfile =
            customerProfileRepository.findByStudentCard(customerProfile.getStudentCard());

        assertEquals(customerProfile, actualCustomerProfile);
    }

    @Test
    void newCustomer_findByStudentCard_customerNotFoundExceptionIsThrown() {
        CustomerProfile customerProfile = customerProfileFixture
            .withId(CUSTOMER_ID)
            .build();

        assertThrows(
            CustomerProfileNotFoundException.class,
            () ->
                customerProfileRepository.findByStudentCard(
                    customerProfile.getStudentCard()
                )
        );
    }

    @Test
    void existingCustomers_findAll_allCustomersAreReturned() {
        CustomerProfile anotherCustomerProfile = customerProfileFixture
            .withId(CUSTOMER_ID)
            .build();
        customerProfileRepository.save(customerProfile);
        customerProfileRepository.save(anotherCustomerProfile);
        int expectedSize = 2;

        int actualSize = customerProfileRepository.findAll().size();

        assertEquals(expectedSize, actualSize);
    }
}

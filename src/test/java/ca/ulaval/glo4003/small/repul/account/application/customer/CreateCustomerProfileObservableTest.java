package ca.ulaval.glo4003.small.repul.account.application.customer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.event.account.customer.CreateCustomerProfileObserver;
import ca.ulaval.glo4003.event.account.customer.CustomerCreatedEvent;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.customer.CreateCustomerProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import org.junit.jupiter.api.Test;

class CreateCustomerProfileObservableTest {

    private static final CustomerCreatedEvent CUSTOMER_CREATED_EVENT =
        new CustomerCreatedEvent(
            new CustomerProfileId("idul"),
            new EmailAddress("email"),
            new Password("password")
        );

    private final CreateCustomerProfileObserver observerMock = mock(
        CreateCustomerProfileObserver.class
    );
    private final CreateCustomerProfileObservable createCustomerProfileObservable =
        new CreateCustomerProfileObservable();

    @Test
    void notifyCustomerCreated_listenCustomerCreatedIsCalled() {
        createCustomerProfileObservable.register(observerMock);

        createCustomerProfileObservable.notifyCustomerCreated(CUSTOMER_CREATED_EVENT);

        verify(observerMock).listenCustomerCreated(CUSTOMER_CREATED_EVENT);
    }
}

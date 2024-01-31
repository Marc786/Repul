package ca.ulaval.glo4003.small.repul.account.application.carrier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.event.account.carrier.CarrierCreatedEvent;
import ca.ulaval.glo4003.event.account.carrier.CreateCarrierProfileObserver;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.carrier.CreateCarrierProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileId;
import org.junit.jupiter.api.Test;

class CreateCarrierProfileObservableTest {

    private static final CarrierCreatedEvent CARRIER_PROFILE_EVENT =
        new CarrierCreatedEvent(
            new CarrierProfileId(),
            new EmailAddress("foo@bar.bin"),
            new Password("password")
        );

    private final CreateCarrierProfileObserver observerMock = mock(
        CreateCarrierProfileObserver.class
    );
    private final CreateCarrierProfileObservable createCarrierProfileObservable =
        new CreateCarrierProfileObservable();

    @Test
    void notifyCarrierCreated_listenCarrierCreatedIsCalled() {
        createCarrierProfileObservable.register(observerMock);

        createCarrierProfileObservable.notifyCarrierCreated(CARRIER_PROFILE_EVENT);

        verify(observerMock).listenCarrierCreated(CARRIER_PROFILE_EVENT);
    }
}

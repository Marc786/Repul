package ca.ulaval.glo4003.small.repul.account.application.cook;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.event.account.cook.CookCreatedEvent;
import ca.ulaval.glo4003.event.account.cook.CreateCookProfileObserver;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.cook.CreateCookProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileId;
import org.junit.jupiter.api.Test;

class CreateCookProfileObservableTest {

    private static final CookCreatedEvent COOK_CREATED_EVENT = new CookCreatedEvent(
        new CookProfileId(),
        new EmailAddress("email"),
        new Password("password")
    );

    private final CreateCookProfileObserver observerMock = mock(
        CreateCookProfileObserver.class
    );
    private final CreateCookProfileObservable createCookProfileObservable =
        new CreateCookProfileObservable();

    @Test
    void notifyCookCreated_listenCookCreatedIsCalled() {
        createCookProfileObservable.register(observerMock);

        createCookProfileObservable.notifyCookCreated(COOK_CREATED_EVENT);

        verify(observerMock).listenCookCreated(COOK_CREATED_EVENT);
    }
}

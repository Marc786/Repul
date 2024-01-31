package ca.ulaval.glo4003.small.repul.account.application.cook;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.event.account.cook.CookCreatedEvent;
import ca.ulaval.glo4003.fixture.account.CookProfileFixture;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.cook.CookProfileService;
import ca.ulaval.glo4003.repul.account.application.cook.CreateCookProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfile;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileId;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.cook.exception.CookProfileAlreadyExistsException;
import ca.ulaval.glo4003.repul.account.domain.cook.exception.CookProfileNotFoundException;
import org.junit.jupiter.api.Test;

class CookProfileServiceTest {

    private static final Password PASSWORD = new Password("password");
    private final CookProfileFixture cookProfileFixture = new CookProfileFixture();
    private final CookProfile cookProfile = cookProfileFixture.build();

    private final CookProfileRepository cookProfileRepositoryMock = mock(
        CookProfileRepository.class
    );
    private final CreateCookProfileObservable createCookProfileObservableMock = mock(
        CreateCookProfileObservable.class
    );

    private final CookProfileService cookProfileService = new CookProfileService(
        cookProfileRepositoryMock,
        createCookProfileObservableMock
    );

    @Test
    void createCook_cookIsNotified() {
        when(cookProfileRepositoryMock.findByEmail(any()))
            .thenThrow(new CookProfileNotFoundException(cookProfile.getEmail()));

        CookProfileId cookProfileId = cookProfileService.createCook(
            cookProfile.getName(),
            cookProfile.getEmail(),
            PASSWORD
        );

        CookCreatedEvent expectedCookCreatedEvent = new CookCreatedEvent(
            cookProfileId,
            cookProfile.getEmail(),
            PASSWORD
        );
        verify(createCookProfileObservableMock)
            .notifyCookCreated(expectedCookCreatedEvent);
    }

    @Test
    void existingCook_createCook_throwsCookAlreadyExistsException() {
        when(cookProfileRepositoryMock.findByEmail(any())).thenReturn(cookProfile);

        assertThrows(
            CookProfileAlreadyExistsException.class,
            () ->
                cookProfileService.createCook(
                    cookProfile.getName(),
                    cookProfile.getEmail(),
                    PASSWORD
                )
        );
    }

    @Test
    void existingCook_createCook_notifyIsNotCalled() {
        when(cookProfileRepositoryMock.findByEmail(any())).thenReturn(cookProfile);

        verify(createCookProfileObservableMock, never()).notifyCookCreated(any());
    }

    @Test
    void existingCook_createCook_saveIsNotCalled() {
        when(cookProfileRepositoryMock.findByEmail(any())).thenReturn(cookProfile);

        verify(cookProfileRepositoryMock, never()).save(any());
    }
}

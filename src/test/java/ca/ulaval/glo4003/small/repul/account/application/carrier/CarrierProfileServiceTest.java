package ca.ulaval.glo4003.small.repul.account.application.carrier;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.event.account.carrier.CarrierCreatedEvent;
import ca.ulaval.glo4003.fixture.account.CarrierProfileFixture;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.carrier.CarrierProfileService;
import ca.ulaval.glo4003.repul.account.application.carrier.CreateCarrierProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfile;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileId;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.carrier.exception.CarrierProfileAlreadyExistsException;
import ca.ulaval.glo4003.repul.account.domain.carrier.exception.CarrierProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCarrierProfileRepository;
import org.junit.jupiter.api.Test;

class CarrierProfileServiceTest {

    private static final Password PASSWORD = new Password("password");
    private final CarrierProfileFixture carrierProfileFixture =
        new CarrierProfileFixture();
    private final CarrierProfile carrierProfile = carrierProfileFixture.build();
    private final CarrierProfileRepository carrierProfileRepositoryMock = mock(
        InMemoryCarrierProfileRepository.class
    );
    private final CreateCarrierProfileObservable createCarrierProfileObservableMock =
        mock(CreateCarrierProfileObservable.class);
    private final CarrierProfileService carrierProfileService = new CarrierProfileService(
        carrierProfileRepositoryMock,
        createCarrierProfileObservableMock
    );

    @Test
    void createCarrier_carrierIsNotified() {
        when(carrierProfileRepositoryMock.findByEmail(carrierProfile.getEmail()))
            .thenThrow(new CarrierProfileNotFoundException(carrierProfile.getEmail()));

        CarrierProfileId carrierProfileId = carrierProfileService.createCarrier(
            carrierProfile.getName(),
            carrierProfile.getEmail(),
            PASSWORD
        );

        CarrierCreatedEvent expectedCarrierCreatedEvent = new CarrierCreatedEvent(
            carrierProfileId,
            carrierProfile.getEmail(),
            PASSWORD
        );
        verify(createCarrierProfileObservableMock)
            .notifyCarrierCreated(expectedCarrierCreatedEvent);
    }

    @Test
    void existingCarrierWithSameEmail_createCarrier_throwsCarrierAlreadyExistsException() {
        when(carrierProfileRepositoryMock.findByEmail(carrierProfile.getEmail()))
            .thenReturn(carrierProfile);

        assertThrows(
            CarrierProfileAlreadyExistsException.class,
            () ->
                carrierProfileService.createCarrier(
                    carrierProfile.getName(),
                    carrierProfile.getEmail(),
                    PASSWORD
                )
        );
    }

    @Test
    void existingCarrierWithSameEmail_createCarrier_notifyIsNotCalled() {
        when(carrierProfileRepositoryMock.findByEmail(carrierProfile.getEmail()))
            .thenReturn(carrierProfile);

        verify(createCarrierProfileObservableMock, never()).notifyCarrierCreated(any());
    }

    @Test
    void existingCarrierWithSameEmail_createCarrier_saveIsNotCalled() {
        when(carrierProfileRepositoryMock.findByEmail(carrierProfile.getEmail()))
            .thenReturn(carrierProfile);

        verify(carrierProfileRepositoryMock, never()).save(any());
    }
}

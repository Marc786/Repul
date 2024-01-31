package ca.ulaval.glo4003.medium.account.carrier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.fixture.account.CarrierProfileFixture;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.carrier.CarrierProfileService;
import ca.ulaval.glo4003.repul.account.application.carrier.CreateCarrierProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfile;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileId;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.carrier.exception.CarrierProfileAlreadyExistsException;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCarrierProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarrierProfileServiceTest {

    private static final Password PASSWORD = new Password("password");
    private final CarrierProfileFixture carrierProfileFixture =
        new CarrierProfileFixture();
    private final CarrierProfile carrierProfile = carrierProfileFixture.build();
    private final CreateCarrierProfileObservable createCarrierProfileObservableMock =
        mock(CreateCarrierProfileObservable.class);
    private CarrierProfileRepository carrierProfileRepository;
    private CarrierProfileService carrierProfileService;

    @BeforeEach
    void setup() {
        carrierProfileRepository = new InMemoryCarrierProfileRepository();
        carrierProfileService =
            new CarrierProfileService(
                carrierProfileRepository,
                createCarrierProfileObservableMock
            );
    }

    @Test
    void newCarrier_createCarrier_carrierIsSaved() {
        CarrierProfileId carrierProfileId = carrierProfileService.createCarrier(
            carrierProfile.getName(),
            carrierProfile.getEmail(),
            PASSWORD
        );
        CarrierProfile expectedCarrierProfile = carrierProfileFixture
            .withId(carrierProfileId)
            .build();
        CarrierProfile savedCarrierProfile = carrierProfileRepository.findByEmail(
            carrierProfile.getEmail()
        );

        assertEquals(expectedCarrierProfile, savedCarrierProfile);
    }

    @Test
    void existingCarrier_createCarrier_throwsCarrierAlreadyExistsException() {
        carrierProfileRepository.save(carrierProfile);

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
    void getCarriersEmail_carrierExists_returnsCarrierEmail() {
        carrierProfileRepository.save(carrierProfile);

        assertTrue(
            carrierProfileService.getCarriersEmail().contains(carrierProfile.getEmail())
        );
    }
}

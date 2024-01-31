package ca.ulaval.glo4003.small.repul.account.infra;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.fixture.account.CarrierProfileFixture;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfile;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileId;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.carrier.exception.CarrierProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCarrierProfileRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class InMemoryCarrierProfileRepositoryTestProfile {

    private final CarrierProfileFixture carrierProfileFixture =
        new CarrierProfileFixture();
    private final CarrierProfile carrierProfile = carrierProfileFixture.build();
    private final CarrierProfile anotherCarrierProfile = carrierProfileFixture
        .withId(new CarrierProfileId())
        .build();

    private final CarrierProfileRepository carrierProfileRepository =
        new InMemoryCarrierProfileRepository();

    @Test
    void savedCarriers_findAll_carriersAreReturned() {
        carrierProfileRepository.save(carrierProfile);
        carrierProfileRepository.save(anotherCarrierProfile);
        List<CarrierProfile> expectedCarrierProfiles = List.of(
            carrierProfile,
            anotherCarrierProfile
        );

        List<CarrierProfile> actualCarrierProfiles = carrierProfileRepository.findAll();

        assertNotSame(expectedCarrierProfiles, actualCarrierProfiles);
        assertEquals(expectedCarrierProfiles, actualCarrierProfiles);
    }

    @Test
    void savedCarriers_findByEmail_carrierIsReturned() {
        carrierProfileRepository.save(carrierProfile);
        carrierProfileRepository.save(anotherCarrierProfile);

        CarrierProfile actualCarrierProfile = carrierProfileRepository.findByEmail(
            carrierProfile.getEmail()
        );

        assertNotSame(carrierProfile, actualCarrierProfile);
        assertEquals(carrierProfile, actualCarrierProfile);
    }

    @Test
    void newCarrier_findByEmail_carrierNotFoundExceptionIsThrown() {
        assertThrows(
            CarrierProfileNotFoundException.class,
            () -> carrierProfileRepository.findByEmail(carrierProfile.getEmail())
        );
    }
}

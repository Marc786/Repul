package ca.ulaval.glo4003.small.repul.account.infra;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.fixture.account.CookProfileFixture;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfile;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.cook.exception.CookProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCookProfileProfileRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class InMemoryCookProfileRepositoryTest {

    private final CookProfileFixture cookProfileFixture = new CookProfileFixture();
    private final CookProfile cookProfile = cookProfileFixture.build();
    private final CookProfile anotherCookProfile = cookProfileFixture.build();

    private final CookProfileRepository cookProfileRepository =
        new InMemoryCookProfileProfileRepository();

    @Test
    void savedCooks_findAll_cooksAreReturned() {
        cookProfileRepository.save(cookProfile);
        cookProfileRepository.save(anotherCookProfile);
        List<CookProfile> expectedCookProfiles = cookProfileRepository.findAll();

        List<CookProfile> actualCookProfiles = cookProfileRepository.findAll();

        assertNotSame(expectedCookProfiles, actualCookProfiles);
        assertTrue(actualCookProfiles.contains(cookProfile));
    }

    @Test
    void savedCooks_findByEmail_cookIsReturned() {
        cookProfileRepository.save(cookProfile);
        cookProfileRepository.save(anotherCookProfile);

        CookProfile actualCookProfile = cookProfileRepository.findByEmail(
            cookProfile.getEmail()
        );

        assertNotSame(cookProfile, actualCookProfile);
        assertEquals(cookProfile, actualCookProfile);
    }

    @Test
    void newCook_findByEmail_cookNotFoundExceptionIsThrown() {
        assertThrows(
            CookProfileNotFoundException.class,
            () -> cookProfileRepository.findByEmail(cookProfile.getEmail())
        );
    }
}

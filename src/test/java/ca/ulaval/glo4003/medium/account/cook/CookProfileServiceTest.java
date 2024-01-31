package ca.ulaval.glo4003.medium.account.cook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.fixture.account.CookProfileFixture;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.application.cook.CookProfileService;
import ca.ulaval.glo4003.repul.account.application.cook.CreateCookProfileObservable;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfile;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileRepository;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCookProfileProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookProfileServiceTest {

    private static final Password PASSWORD = new Password("password");
    private final CookProfileFixture cookProfileFixture = new CookProfileFixture();
    private final CookProfile cookProfile = cookProfileFixture.build();
    private final CreateCookProfileObservable createCookProfileObservableMock = mock(
        CreateCookProfileObservable.class
    );

    private CookProfileRepository cookProfileRepository;
    private CookProfileService cookProfileService;

    @BeforeEach
    void setup() {
        cookProfileRepository = new InMemoryCookProfileProfileRepository();
        cookProfileService =
            new CookProfileService(
                cookProfileRepository,
                createCookProfileObservableMock
            );
    }

    @Test
    void createCook_cookIsSaved() {
        cookProfileService.createCook(
            cookProfile.getName(),
            cookProfile.getEmail(),
            PASSWORD
        );

        CookProfile actualCookProfile = cookProfileRepository.findByEmail(
            cookProfile.getEmail()
        );
        assertEquals(cookProfile.getEmail(), actualCookProfile.getEmail());
    }
}

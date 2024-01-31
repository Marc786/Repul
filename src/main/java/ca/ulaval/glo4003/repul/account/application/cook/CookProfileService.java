package ca.ulaval.glo4003.repul.account.application.cook;

import ca.ulaval.glo4003.event.account.cook.CookCreatedEvent;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfile;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileId;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.cook.exception.CookProfileAlreadyExistsException;
import ca.ulaval.glo4003.repul.account.domain.cook.exception.CookProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;

public class CookProfileService {

    private final CookProfileRepository cookProfileRepository;
    private final CreateCookProfileObservable createCookProfileObservable;

    public CookProfileService(
        CookProfileRepository cookProfileRepository,
        CreateCookProfileObservable createCookProfileObservable
    ) {
        this.cookProfileRepository = cookProfileRepository;
        this.createCookProfileObservable = createCookProfileObservable;
    }

    public CookProfileId createCook(
        Name name,
        EmailAddress emailAddress,
        Password password
    ) {
        if (doesCookAlreadyExist(emailAddress)) {
            throw new CookProfileAlreadyExistsException();
        }

        CookProfileId cookProfileId = new CookProfileId();
        CookProfile cookProfile = new CookProfile(cookProfileId, name, emailAddress);

        cookProfileRepository.save(cookProfile);

        CookCreatedEvent cookCreatedEvent = new CookCreatedEvent(
            cookProfile.getId(),
            cookProfile.getEmail(),
            password
        );
        createCookProfileObservable.notifyCookCreated(cookCreatedEvent);

        return cookProfile.getId();
    }

    private boolean doesCookAlreadyExist(EmailAddress cookEmail) {
        try {
            cookProfileRepository.findByEmail(cookEmail);
            return true;
        } catch (CookProfileNotFoundException e) {
            return false;
        }
    }
}

package ca.ulaval.glo4003.repul.account.infra;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfile;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.cook.exception.CookProfileNotFoundException;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCookProfileProfileRepository implements CookProfileRepository {

    private final Cloner cloner = new Cloner();
    private final List<CookProfile> cookProfiles = new ArrayList<>();

    @Override
    public void save(CookProfile cookProfile) {
        removeIfExisting(cookProfile);
        cookProfiles.add(cookProfile);
    }

    @Override
    public CookProfile findByEmail(EmailAddress emailAddress) {
        CookProfile cookProfileFound = cookProfiles
            .stream()
            .filter(cook -> cook.getEmail().equals(emailAddress))
            .findFirst()
            .orElseThrow(() -> new CookProfileNotFoundException(emailAddress));
        return cloner.deepClone(cookProfileFound);
    }

    @Override
    public List<CookProfile> findAll() {
        return cloner.deepClone(cookProfiles);
    }

    private void removeIfExisting(CookProfile cookProfile) {
        cookProfiles.removeIf(existingCookProfile ->
            existingCookProfile.getId().equals(cookProfile.getId())
        );
    }
}

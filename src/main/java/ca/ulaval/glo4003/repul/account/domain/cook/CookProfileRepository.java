package ca.ulaval.glo4003.repul.account.domain.cook;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import java.util.List;

public interface CookProfileRepository {
    void save(CookProfile cookProfile);

    CookProfile findByEmail(EmailAddress emailAddress);

    List<CookProfile> findAll();
}

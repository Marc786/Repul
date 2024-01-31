package ca.ulaval.glo4003.fixture.account;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfile;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileId;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;

public class CookProfileFixture {

    private final CookProfileId id = new CookProfileId();
    private final Name name = new Name("John", "Cook");
    private final EmailAddress email = new EmailAddress("cook@chef.com");

    public CookProfile build() {
        return new CookProfile(id, name, email);
    }
}

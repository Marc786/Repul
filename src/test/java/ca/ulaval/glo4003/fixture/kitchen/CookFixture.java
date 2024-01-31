package ca.ulaval.glo4003.fixture.kitchen;

import ca.ulaval.glo4003.repul.kitchen.domain.cook.Cook;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import java.util.UUID;

public class CookFixture {

    private CookId defaultId = new CookId(UUID.randomUUID().toString());

    public Cook build() {
        return new Cook(defaultId);
    }

    public CookFixture withId(CookId id) {
        defaultId = id;
        return this;
    }
}

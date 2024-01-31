package ca.ulaval.glo4003.fixture.account;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfile;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileId;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;

public class CarrierProfileFixture {

    private CarrierProfileId id = new CarrierProfileId();
    private final Name name = new Name("John", "Carrier");
    private final EmailAddress email = new EmailAddress("carrier@livreur.com");

    public CarrierProfile build() {
        return new CarrierProfile(id, email, name);
    }

    public CarrierProfileFixture withId(CarrierProfileId id) {
        this.id = id;
        return this;
    }
}

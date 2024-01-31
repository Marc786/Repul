package ca.ulaval.glo4003.repul.account.infra;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfile;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.carrier.exception.CarrierProfileNotFoundException;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCarrierProfileRepository implements CarrierProfileRepository {

    private final Cloner cloner = new Cloner();
    private final List<CarrierProfile> carrierProfiles = new ArrayList<>();

    @Override
    public void save(CarrierProfile carrierProfile) {
        removeIfExisting(carrierProfile);
        carrierProfiles.add(carrierProfile);
    }

    @Override
    public CarrierProfile findByEmail(EmailAddress email) {
        CarrierProfile carrierProfileFound = carrierProfiles
            .stream()
            .filter(carrier -> carrier.getEmail().equals(email))
            .findFirst()
            .orElseThrow(() -> new CarrierProfileNotFoundException(email));
        return cloner.deepClone(carrierProfileFound);
    }

    @Override
    public List<CarrierProfile> findAll() {
        return cloner.deepClone(carrierProfiles);
    }

    private void removeIfExisting(CarrierProfile carrierProfile) {
        carrierProfiles.removeIf(existingCarrierProfile ->
            existingCarrierProfile.equals(carrierProfile)
        );
    }
}

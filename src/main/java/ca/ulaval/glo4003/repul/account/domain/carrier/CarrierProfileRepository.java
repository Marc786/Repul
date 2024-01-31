package ca.ulaval.glo4003.repul.account.domain.carrier;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import java.util.List;

public interface CarrierProfileRepository {
    void save(CarrierProfile carrierProfile);

    CarrierProfile findByEmail(EmailAddress email);

    List<CarrierProfile> findAll();
}

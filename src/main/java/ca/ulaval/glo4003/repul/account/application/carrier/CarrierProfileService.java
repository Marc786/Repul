package ca.ulaval.glo4003.repul.account.application.carrier;

import ca.ulaval.glo4003.event.account.carrier.CarrierCreatedEvent;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfile;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileId;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.carrier.exception.CarrierProfileAlreadyExistsException;
import ca.ulaval.glo4003.repul.account.domain.carrier.exception.CarrierProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import java.util.List;

public class CarrierProfileService {

    private final CarrierProfileRepository carrierProfileRepository;
    private final CreateCarrierProfileObservable createCarrierProfileObservable;

    public CarrierProfileService(
        CarrierProfileRepository carrierProfileRepository,
        CreateCarrierProfileObservable createCarrierProfileObservable
    ) {
        this.carrierProfileRepository = carrierProfileRepository;
        this.createCarrierProfileObservable = createCarrierProfileObservable;
    }

    public CarrierProfileId createCarrier(
        Name name,
        EmailAddress emailAddress,
        Password password
    ) {
        if (doesCarrierAlreadyExists(emailAddress)) {
            throw new CarrierProfileAlreadyExistsException(emailAddress);
        }

        CarrierProfileId carrierProfileId = new CarrierProfileId();
        CarrierProfile carrierProfile = new CarrierProfile(
            carrierProfileId,
            emailAddress,
            name
        );

        carrierProfileRepository.save(carrierProfile);

        CarrierCreatedEvent carrierCreatedEvent = new CarrierCreatedEvent(
            carrierProfile.getId(),
            carrierProfile.getEmail(),
            password
        );
        createCarrierProfileObservable.notifyCarrierCreated(carrierCreatedEvent);

        return carrierProfile.getId();
    }

    public List<EmailAddress> getCarriersEmail() {
        return carrierProfileRepository
            .findAll()
            .stream()
            .map(CarrierProfile::getEmail)
            .toList();
    }

    private boolean doesCarrierAlreadyExists(EmailAddress emailAddress) {
        try {
            carrierProfileRepository.findByEmail(emailAddress);
            return true;
        } catch (CarrierProfileNotFoundException e) {
            return false;
        }
    }
}

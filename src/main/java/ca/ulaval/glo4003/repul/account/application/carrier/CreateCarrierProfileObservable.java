package ca.ulaval.glo4003.repul.account.application.carrier;

import ca.ulaval.glo4003.event.account.carrier.CarrierCreatedEvent;
import ca.ulaval.glo4003.event.account.carrier.CreateCarrierProfileObserver;
import java.util.ArrayList;
import java.util.List;

public class CreateCarrierProfileObservable {

    private final List<CreateCarrierProfileObserver> observers = new ArrayList<>();

    public void register(CreateCarrierProfileObserver observer) {
        observers.add(observer);
    }

    public void notifyCarrierCreated(CarrierCreatedEvent carrierCreatedEvent) {
        observers.forEach(observer -> observer.listenCarrierCreated(carrierCreatedEvent));
    }
}

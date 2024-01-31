package ca.ulaval.glo4003.event.account.carrier;

public interface CreateCarrierProfileObserver {
    void listenCarrierCreated(CarrierCreatedEvent carrierCreatedEvent);
}

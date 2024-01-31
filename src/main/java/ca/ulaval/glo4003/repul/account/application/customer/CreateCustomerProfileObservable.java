package ca.ulaval.glo4003.repul.account.application.customer;

import ca.ulaval.glo4003.event.account.customer.CreateCustomerProfileObserver;
import ca.ulaval.glo4003.event.account.customer.CustomerCreatedEvent;
import java.util.ArrayList;
import java.util.List;

public class CreateCustomerProfileObservable {

    private final List<CreateCustomerProfileObserver> observers = new ArrayList<>();

    public void register(CreateCustomerProfileObserver observer) {
        observers.add(observer);
    }

    public void notifyCustomerCreated(CustomerCreatedEvent customerCreatedEvent) {
        observers.forEach(observer -> observer.listenCustomerCreated(customerCreatedEvent)
        );
    }
}

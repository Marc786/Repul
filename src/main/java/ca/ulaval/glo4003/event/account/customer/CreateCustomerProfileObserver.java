package ca.ulaval.glo4003.event.account.customer;

public interface CreateCustomerProfileObserver {
    void listenCustomerCreated(CustomerCreatedEvent customerCreatedEvent);
}

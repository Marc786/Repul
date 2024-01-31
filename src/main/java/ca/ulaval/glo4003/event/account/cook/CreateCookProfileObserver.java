package ca.ulaval.glo4003.event.account.cook;

public interface CreateCookProfileObserver {
    void listenCookCreated(CookCreatedEvent cookCreatedEvent);
}

package ca.ulaval.glo4003.repul.account.application.cook;

import ca.ulaval.glo4003.event.account.cook.CookCreatedEvent;
import ca.ulaval.glo4003.event.account.cook.CreateCookProfileObserver;
import java.util.ArrayList;
import java.util.List;

public class CreateCookProfileObservable {

    private final List<CreateCookProfileObserver> observers = new ArrayList<>();

    public void register(CreateCookProfileObserver observer) {
        observers.add(observer);
    }

    public void notifyCookCreated(CookCreatedEvent cookCreatedEvent) {
        observers.forEach(observer -> observer.listenCookCreated(cookCreatedEvent));
    }
}

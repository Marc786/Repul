package ca.ulaval.glo4003.repul.subscription.infra;

import ca.ulaval.glo4003.repul.subscription.domain.Subscriber;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberRepository;
import ca.ulaval.glo4003.repul.subscription.infra.cloner.SubscriberFastCloner;
import ca.ulaval.glo4003.repul.subscription.infra.exception.SubscriberNotFoundException;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class InMemorySubscriberRepository implements SubscriberRepository {

    private final Cloner cloner = new Cloner();
    private final List<Subscriber> subscribers = new ArrayList<>();

    public InMemorySubscriberRepository() {
        cloner.registerFastCloner(Subscriber.class, new SubscriberFastCloner());
    }

    @Override
    public void save(Subscriber subscriber) {
        removeIfExisting(subscriber);
        subscribers.add(subscriber);
    }

    @Override
    public Subscriber findById(SubscriberId id) {
        Subscriber subscriberFound = subscribers
            .stream()
            .filter(subscriber -> subscriber.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new SubscriberNotFoundException(id));

        return cloner.deepClone(subscriberFound);
    }

    private void removeIfExisting(Subscriber subscriber) {
        subscribers.removeIf(existingSubscriber ->
            existingSubscriber.getId().equals(subscriber.getId())
        );
    }
}

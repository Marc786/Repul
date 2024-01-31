package ca.ulaval.glo4003.repul.subscription.domain;

public interface SubscriberRepository {
    void save(Subscriber subscriber);

    Subscriber findById(SubscriberId id);
}

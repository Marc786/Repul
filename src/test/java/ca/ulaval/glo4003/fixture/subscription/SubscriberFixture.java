package ca.ulaval.glo4003.fixture.subscription;

import ca.ulaval.glo4003.repul.subscription.domain.Subscriber;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;

public class SubscriberFixture {

    private SubscriberId subscriberId = new SubscriberId("123");
    private final SubscriptionFixture subscriptionFixture = new SubscriptionFixture();

    public Subscriber build() {
        return new Subscriber(subscriberId);
    }

    public Subscriber buildWithExistingSubscription() {
        Subscriber subscriber = new Subscriber(subscriberId);
        subscriber.addSubscription(subscriptionFixture.build());
        return subscriber;
    }

    public Subscriber buildWithExistingSubscriptions(SubscriptionId subscriptionId) {
        Subscriber subscriber = new Subscriber(subscriberId);
        subscriber.addSubscription(subscriptionFixture.withId(subscriptionId).build());
        return subscriber;
    }

    public SubscriberFixture withId(SubscriberId id) {
        this.subscriberId = id;
        return this;
    }
}

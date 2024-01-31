package ca.ulaval.glo4003.small.repul.subscription.infra;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.fixture.subscription.SubscriberFixture;
import ca.ulaval.glo4003.repul.subscription.domain.Subscriber;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.infra.InMemorySubscriberRepository;
import ca.ulaval.glo4003.repul.subscription.infra.exception.SubscriberNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemorySubscriberRepositoryTest {

    private final SubscriberFixture subscriberFixture = new SubscriberFixture();
    private InMemorySubscriberRepository inMemorySubscriberRepository;

    @BeforeEach
    void setup() {
        inMemorySubscriberRepository = new InMemorySubscriberRepository();
    }

    @Test
    void getById_deepCopySubscriberIsReturned() {
        Subscriber subscriber = subscriberFixture.build();

        inMemorySubscriberRepository.save(subscriber);

        Subscriber actualSubscriber = inMemorySubscriberRepository.findById(
            subscriber.getId()
        );

        assertNotSame(subscriber, actualSubscriber);
        assertEquals(subscriber, actualSubscriber);
    }

    @Test
    void save_subscriberIsSaved() {
        Subscriber subscriber = subscriberFixture.build();

        inMemorySubscriberRepository.save(subscriber);

        Subscriber actualSubscriber = inMemorySubscriberRepository.findById(
            subscriber.getId()
        );

        assertEquals(subscriber.getId(), actualSubscriber.getId());
        assertEquals(subscriber.getSubscriptions(), actualSubscriber.getSubscriptions());
    }

    @Test
    void existingSubscriber_saveWithExistingSubscriber_newSubscriberReplaceExistingSubscriber() {
        Subscriber subscriberWithExistingSubscription =
            subscriberFixture.buildWithExistingSubscription();
        inMemorySubscriberRepository.save(subscriberWithExistingSubscription);
        Subscriber newSubscriber = subscriberFixture.build();

        inMemorySubscriberRepository.save(newSubscriber);

        Subscriber actualSubscriber = inMemorySubscriberRepository.findById(
            subscriberWithExistingSubscription.getId()
        );
        assertEquals(newSubscriber.getId(), actualSubscriber.getId());
        assertEquals(
            newSubscriber.getSubscriptions(),
            actualSubscriber.getSubscriptions()
        );
    }

    @Test
    void getById_returnsCorrespondingSubscriber() {
        Subscriber subscriber = subscriberFixture.build();
        inMemorySubscriberRepository.save(subscriber);

        Subscriber actualSubscriber = inMemorySubscriberRepository.findById(
            subscriber.getId()
        );

        assertEquals(subscriber.getId(), actualSubscriber.getId());
        assertEquals(subscriber.getSubscriptions(), actualSubscriber.getSubscriptions());
    }

    @Test
    void getByIdWithNonExistingSubscriber_throwsException() {
        Subscriber subscriber = subscriberFixture.build();
        inMemorySubscriberRepository.save(subscriber);
        SubscriberId noneExistingSubscriberId = new SubscriberId("456");

        assertThrows(
            SubscriberNotFoundException.class,
            () -> inMemorySubscriberRepository.findById(noneExistingSubscriberId)
        );
    }
}

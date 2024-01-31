package ca.ulaval.glo4003.small.repul.subscription.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.fixture.subscription.SubscriberFixture;
import ca.ulaval.glo4003.lib.semester.registry.finder.SubscriptionSemesterFinder;
import ca.ulaval.glo4003.repul.subscription.application.SubscriberService;
import ca.ulaval.glo4003.repul.subscription.domain.BillClient;
import ca.ulaval.glo4003.repul.subscription.domain.MealClient;
import ca.ulaval.glo4003.repul.subscription.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.subscription.domain.Subscriber;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.SubscriptionFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import ca.ulaval.glo4003.repul.subscription.infra.InMemorySubscriberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriberServiceTest {

    private static final boolean IS_ACCEPTED = true;
    private static final SubscriberFixture subscriberFixture = new SubscriberFixture();
    private static final SubscriberId SUBSCRIBER_ID = new SubscriberId("123");
    private static final SubscriptionId SUBSCRIPTION_ID = new SubscriptionId();
    private static Subscriber SUBSCRIBER;
    private final ShipmentClient shipmentClientMock = mock(ShipmentClient.class);
    private final MealClient mealClientMock = mock(MealClient.class);
    private final BillClient billClientMock = mock(BillClient.class);
    private final SubscriptionSemesterFinder subscriptionSemesterFinderMock = mock(
        SubscriptionSemesterFinder.class
    );
    private final InMemorySubscriberRepository subscriberRepositoryMock = mock(
        InMemorySubscriberRepository.class
    );
    private SubscriberService subscriberService;

    @BeforeEach
    void setup() {
        subscriberService =
            new SubscriberService(
                new SubscriptionFactory(),
                subscriberRepositoryMock,
                subscriptionSemesterFinderMock,
                billClientMock,
                mealClientMock,
                shipmentClientMock
            );
        SUBSCRIBER =
            subscriberFixture
                .withId(SUBSCRIBER_ID)
                .buildWithExistingSubscriptions(SUBSCRIPTION_ID);
    }

    @AfterEach
    void teardown() {
        reset(
            subscriberRepositoryMock,
            subscriptionSemesterFinderMock,
            billClientMock,
            mealClientMock,
            shipmentClientMock
        );
    }

    @Test
    void confirmSubscription_clientIsBilled() {
        when(subscriberRepositoryMock.findById(SUBSCRIBER_ID)).thenReturn(SUBSCRIBER);

        subscriberService.confirmMealKit(SUBSCRIBER_ID, SUBSCRIPTION_ID, IS_ACCEPTED);

        MealKit expectedMealKit = SUBSCRIBER
            .getSubscriptions()
            .get(0)
            .getMealKits()
            .get(0);
        verify(billClientMock).bill(SUBSCRIBER_ID, expectedMealKit);
    }

    @Test
    void confirmSubscription_mealIsAddedToPrepare() {
        when(subscriberRepositoryMock.findById(SUBSCRIBER_ID)).thenReturn(SUBSCRIBER);

        subscriberService.confirmMealKit(SUBSCRIBER_ID, SUBSCRIPTION_ID, IS_ACCEPTED);

        MealKit expectedMealKit = SUBSCRIBER
            .getSubscriptions()
            .get(0)
            .getMealKits()
            .get(0);
        verify(mealClientMock).addMealToPrepare(expectedMealKit);
    }
}

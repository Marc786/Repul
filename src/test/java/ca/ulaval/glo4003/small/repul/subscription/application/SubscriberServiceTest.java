package ca.ulaval.glo4003.small.repul.subscription.application;

import ca.ulaval.glo4003.fixture.subscription.SubscriberFixture;
import ca.ulaval.glo4003.lib.semester.registry.finder.SubscriptionSemesterFinder;
import ca.ulaval.glo4003.repul.subscription.application.SubscriberService;
import ca.ulaval.glo4003.repul.subscription.domain.*;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.SubscriptionFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import ca.ulaval.glo4003.repul.subscription.infra.InMemorySubscriberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup.resetClock;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.setClock;
import static org.mockito.Mockito.*;

class SubscriberServiceTest {

    private final LocalDate NOW = LocalDate.of(2024, 2, 1);
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
        resetClock();
    }

    @Test
    void confirmSubscription_clientIsBilled() {
        setClock(NOW);
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
        setClock(NOW);
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

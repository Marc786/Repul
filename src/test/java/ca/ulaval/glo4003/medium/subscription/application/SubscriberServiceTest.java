package ca.ulaval.glo4003.medium.subscription.application;

import ca.ulaval.glo4003.fixture.semester.SemesterFixture;
import ca.ulaval.glo4003.fixture.subscription.SubscriberFixture;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.registry.finder.SubscriptionSemesterFinder;
import ca.ulaval.glo4003.repul.subscription.application.SubscriberService;
import ca.ulaval.glo4003.repul.subscription.domain.*;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.SubscriptionFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.exception.SubscriptionDoesNotBelongToSubscriberException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.SubscriberConfirmationStatus;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import ca.ulaval.glo4003.repul.subscription.infra.InMemorySubscriberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup.resetClock;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.setClock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubscriberServiceTest {

    private static final SubscriberId SUBSCRIBER_ID = new SubscriberId("me1234");
    private static final LocalDate NOW = LocalDate.of(2023, 1, 15);
    private static final LocalDate DELIVERY_START_DATE = LocalDate.of(2023, 3, 13);
    private static final LocalDate SEMESTER_START_DATE = LocalDate.of(2023, 1, 1);
    private static final LocalDate SEMESTER_END_DATE = LocalDate.of(2023, 4, 30);
    private final SemesterFixture semesterFixture = new SemesterFixture();
    private final SubscriberFixture subscriberFixture = new SubscriberFixture();
    private final Semester SEMESTER = semesterFixture
        .withStartDate(SEMESTER_START_DATE)
        .withEndDate(SEMESTER_END_DATE)
        .build();
    private final ShipmentClient shipmentClientMock = mock(ShipmentClient.class);
    private final MealClient mealClientMock = mock(MealClient.class);
    private final BillClient billClientMock = mock(BillClient.class);
    private final SubscriptionSemesterFinder subscriptionSemesterFinderMock = mock(
        SubscriptionSemesterFinder.class
    );
    private InMemorySubscriberRepository subscriberRepository;
    private SubscriberService subscriberService;

    @BeforeEach
    void setup() {
        when(subscriptionSemesterFinderMock.findCurrentOrNextSemester(any()))
            .thenReturn(SEMESTER);
        subscriberRepository = new InMemorySubscriberRepository();
        subscriberService =
            new SubscriberService(
                new SubscriptionFactory(),
                subscriberRepository,
                subscriptionSemesterFinderMock,
                billClientMock,
                mealClientMock,
                shipmentClientMock
            );
    }

    @AfterEach
    void teardown() {
        resetClock();
    }

    @Test
    void existingSubscriber_addSporadicSubscription_subscriptionIsAdded() {
        Subscriber subscriberWithExistingSubscription = subscriberFixture
            .withId(SUBSCRIBER_ID)
            .buildWithExistingSubscription();
        subscriberRepository.save(subscriberWithExistingSubscription);
        int expectedNumberOfSubscription = 2;

        subscriberService.addSporadicSubscription(SUBSCRIBER_ID);

        int actualNumberOfSubscription = subscriberRepository
            .findById(SUBSCRIBER_ID)
            .getSubscriptions()
            .size();
        assertEquals(expectedNumberOfSubscription, actualNumberOfSubscription);
    }

    @Test
    void nonExistingSubscriber_addSporadicSubscription_subscriberNotFoundExceptionIsThrown() {
        subscriberService.addSporadicSubscription(SUBSCRIBER_ID);
        int expectedNumberOfSubscription = 1;

        int actualNumberOfSubscription = subscriberRepository
            .findById(SUBSCRIBER_ID)
            .getSubscriptions()
            .size();
        assertEquals(expectedNumberOfSubscription, actualNumberOfSubscription);
    }

    @Test
    void existingSubscriber_addRecurringSubscription_subscriptionIsAdded() {
        setClock(NOW);
        Subscriber subscriberWithExistingSubscription = subscriberFixture
            .withId(SUBSCRIBER_ID)
            .buildWithExistingSubscription();
        subscriberRepository.save(subscriberWithExistingSubscription);
        int expectedNumberOfSubscription = 2;

        subscriberService.addRecurringSubscription(
            SUBSCRIBER_ID,
            DayOfWeek.MONDAY,
            DELIVERY_START_DATE,
            Frequency.WEEKLY,
            PickupPointLocation.DESJARDINS,
            MealKitType.STANDARD
        );

        int actualNumberOfSubscription = subscriberRepository
            .findById(SUBSCRIBER_ID)
            .getSubscriptions()
            .size();
        assertEquals(expectedNumberOfSubscription, actualNumberOfSubscription);
    }

    @Test
    void nonExistingSubscriber_addRecurringSubscription_subscriptionIsAdded() {
        setClock(NOW);
        int expectedNumberOfSubscription = 1;

        subscriberService.addRecurringSubscription(
            SUBSCRIBER_ID,
            DayOfWeek.MONDAY,
            DELIVERY_START_DATE,
            Frequency.WEEKLY,
            PickupPointLocation.DESJARDINS,
            MealKitType.STANDARD
        );

        int actualNumberOfSubscription = subscriberRepository
            .findById(SUBSCRIBER_ID)
            .getSubscriptions()
            .size();
        assertEquals(expectedNumberOfSubscription, actualNumberOfSubscription);
    }

    @Test
    void existingSubscriberWithSubscriptions_getSubscriptions_subscriptionsAreReturned() {
        setClock(NOW);
        subscriberService.addRecurringSubscription(
            SUBSCRIBER_ID,
            DayOfWeek.MONDAY,
            DELIVERY_START_DATE,
            Frequency.WEEKLY,
            PickupPointLocation.DESJARDINS,
            MealKitType.STANDARD
        );
        subscriberService.addSporadicSubscription(SUBSCRIBER_ID);
        int expectedNumberOfSubscription = 2;

        List<Subscription> actualSubscriptions = subscriberService.getSubscriptions(
            SUBSCRIBER_ID
        );

        assertEquals(expectedNumberOfSubscription, actualSubscriptions.size());
    }

    @Test
    void nonExistingSubscriber_getSubscriptions_emptyListIsReturned() {
        int expectedNumberOfSubscription = 0;

        List<Subscription> actualSubscriptions = subscriberService.getSubscriptions(
            SUBSCRIBER_ID
        );

        assertEquals(expectedNumberOfSubscription, actualSubscriptions.size());
    }

    @Test
    void existingSubscriber_confirmMealKit_mealKitIsPaid() {
        setClock(NOW);
        Subscriber subscriberWithExistingSubscription = subscriberFixture
            .withId(SUBSCRIBER_ID)
            .buildWithExistingSubscription();
        SubscriptionId subscriptionId = subscriberWithExistingSubscription
            .getSubscriptions()
            .get(0)
            .getId();
        subscriberRepository.save(subscriberWithExistingSubscription);

        subscriberService.confirmMealKit(SUBSCRIBER_ID, subscriptionId, true);

        Subscriber subscriber = subscriberRepository.findById(SUBSCRIBER_ID);
        SubscriberConfirmationStatus actualSubscriberConfirmationStatus = subscriber
            .getMealKits()
            .get(0)
            .getSubscriberConfirmationStatus();
        assertEquals(
            SubscriberConfirmationStatus.PAID,
            actualSubscriberConfirmationStatus
        );
    }

    @Test
    void refuseMealKit_confirmMealKit_mealKitIsRefused() {
        setClock(NOW);
        Subscriber subscriberWithExistingSubscription = subscriberFixture
            .withId(SUBSCRIBER_ID)
            .buildWithExistingSubscription();
        SubscriptionId subscriptionId = subscriberWithExistingSubscription
            .getSubscriptions()
            .get(0)
            .getId();
        subscriberRepository.save(subscriberWithExistingSubscription);

        subscriberService.confirmMealKit(SUBSCRIBER_ID, subscriptionId, false);

        Subscriber subscriber = subscriberRepository.findById(SUBSCRIBER_ID);
        SubscriberConfirmationStatus actualSubscriberConfirmationStatus = subscriber
            .getMealKits()
            .get(0)
            .getSubscriberConfirmationStatus();
        assertEquals(
            SubscriberConfirmationStatus.REFUSED,
            actualSubscriberConfirmationStatus
        );
    }

    @Test
    void invalidSubscriptionId_confirmMealKit_subscriptionDoesNotBelongToSubscriberExceptionIsThrown() {
        assertThrows(
            SubscriptionDoesNotBelongToSubscriberException.class,
            () ->
                subscriberService.confirmMealKit(
                    SUBSCRIBER_ID,
                    new SubscriptionId(),
                    true
                )
        );
    }

    @Test
    void subscriberNotFound_confirmMealKit_subscriptionDoesNotBelongToSubscriberExceptionIsThrown() {
        SubscriberId NON_EXISTING_SUBSCRIBER_ID = new SubscriberId(
            "nonExistingSubscriberId"
        );

        assertThrows(
            SubscriptionDoesNotBelongToSubscriberException.class,
            () ->
                subscriberService.confirmMealKit(
                    NON_EXISTING_SUBSCRIBER_ID,
                    new SubscriptionId(),
                    true
                )
        );
    }

    @Test
    void nonExistingSubscriber_getMealKits_emptyListIsReturned() {
        int expectedNumberOfMealKits = 0;

        List<MealKit> actualMealKits = subscriberService.getMealKits(SUBSCRIBER_ID);

        assertEquals(expectedNumberOfMealKits, actualMealKits.size());
    }
}

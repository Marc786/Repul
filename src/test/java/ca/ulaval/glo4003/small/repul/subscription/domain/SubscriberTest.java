package ca.ulaval.glo4003.small.repul.subscription.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.fixture.subscription.SubscriptionFixture;
import ca.ulaval.glo4003.repul.subscription.domain.*;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.SubscriberConfirmationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubscriberTest {

    private static final SubscriberId SUBSCRIBER_ID = new SubscriberId("subscriberId");
    private static final SubscriptionFixture subscriptionFixture =
        new SubscriptionFixture();
    private static Subscription subscription;
    private static final BillClient billClient = mock(BillClient.class);
    private static final MealClient mealClientMock = mock(MealClient.class);
    private static final ShipmentClient shipmentClientMock = mock(ShipmentClient.class);
    private Subscriber subscriber;

    @BeforeEach
    void setUp() {
        subscriber = new Subscriber(SUBSCRIBER_ID);
        subscription = subscriptionFixture.build();
    }

    @Test
    void addSubscription_subscriptionIsAdded() {
        subscriber.addSubscription(subscription);

        assertEquals(subscription, subscriber.getSubscriptions().get(0));
    }

    @Test
    void confirmNextMealKit_subscriptionIsConfirmed() {
        subscription.generateNextMealKit();
        subscriber.addSubscription(subscription);

        subscriber.confirmNextMealKit(subscription.getId(), billClient, mealClientMock);

        verify(billClient).bill(subscriber.getId(), subscription.getNextMealKit());
        verify(mealClientMock).addMealToPrepare(subscription.getNextMealKit());
    }

    @Test
    void refuseNextMealKit_subscriptionIsRefused() {
        subscription.generateNextMealKit();
        subscriber.addSubscription(subscription);

        subscriber.refuseNextMealKit(subscription.getId());

        MealKit mealKit = subscription.getMealKits().get(0);
        assertEquals(
            SubscriberConfirmationStatus.REFUSED,
            mealKit.getSubscriberConfirmationStatus()
        );
    }

    @Test
    void generateNextMealKit_subscriptionIsGenerated() {
        subscriber.addSubscription(subscription);

        subscriber.generateNextMealKit(subscription.getId());

        MealKit mealKit = subscription.getMealKits().get(0);
        assertEquals(
            SubscriberConfirmationStatus.PENDING,
            mealKit.getSubscriberConfirmationStatus()
        );
    }

    @Test
    void addMealToPrepare_mealIsAddedToPrepare() {
        subscription.generateNextMealKit();
        subscriber.addSubscription(subscription);

        subscriber.addMealToPrepare(subscription.getId(), mealClientMock);

        verify(mealClientMock).addMealToPrepare(subscription.getNextMealKit());
    }

    @Test
    void addConfirmedMealKitShipmentItem_confirmedMealKitShipmentItemIsAdded() {
        subscription.generateNextMealKit();
        subscriber.addSubscription(subscription);

        subscriber.addConfirmedMealKitShipmentItem(
            subscription.getId(),
            shipmentClientMock
        );

        verify(shipmentClientMock)
            .addConfirmedMealKitShipment(subscription.getNextMealKit());
    }
}

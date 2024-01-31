package ca.ulaval.glo4003.small.repul.subscription.domain.subscription;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.subscription.SubscriptionFixture;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.SubscriberConfirmationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionTest {

    private final SubscriptionFixture subscriptionFixture = new SubscriptionFixture();
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = subscriptionFixture.build();
    }

    @Test
    void confirm_nextMealKitIsConfirmed() {
        subscription.generateNextMealKit();

        subscription.confirmNextMealKit();

        MealKit mealKit = subscription.getNextMealKit();
        assertEquals(
            SubscriberConfirmationStatus.PAID,
            mealKit.getSubscriberConfirmationStatus()
        );
    }

    @Test
    void refuse_nextMealKitIsRefused() {
        subscription.generateNextMealKit();

        subscription.refuseNextMealKit();

        MealKit mealKit = subscription.getNextMealKit();
        assertEquals(
            SubscriberConfirmationStatus.REFUSED,
            mealKit.getSubscriberConfirmationStatus()
        );
    }

    @Test
    void generateNextMealKit_nextMealKitIsGenerated() {
        subscription.generateNextMealKit();

        MealKit mealKit = subscription.getNextMealKit();
        assertEquals(
            SubscriberConfirmationStatus.PENDING,
            mealKit.getSubscriberConfirmationStatus()
        );
    }
}

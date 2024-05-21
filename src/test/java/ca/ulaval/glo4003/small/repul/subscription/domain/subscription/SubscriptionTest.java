package ca.ulaval.glo4003.small.repul.subscription.domain.subscription;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup.resetClock;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.setClock;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.subscription.SubscriptionFixture;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.SubscriberConfirmationStatus;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionTest {

    private final LocalDate NOW = LocalDate.of(2024, 2, 1);
    private final SubscriptionFixture subscriptionFixture = new SubscriptionFixture();
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = subscriptionFixture.build();
    }

    @AfterEach
    void tearDown() {
        resetClock();
    }

    @Test
    void confirm_nextMealKitIsConfirmed() {
        setClock(NOW);
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
        setClock(NOW);
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
        setClock(NOW);

        subscription.generateNextMealKit();

        MealKit mealKit = subscription.getNextMealKit();
        assertEquals(
            SubscriberConfirmationStatus.PENDING,
            mealKit.getSubscriberConfirmationStatus()
        );
    }
}

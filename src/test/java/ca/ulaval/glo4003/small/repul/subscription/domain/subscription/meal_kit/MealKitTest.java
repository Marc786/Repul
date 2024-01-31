package ca.ulaval.glo4003.small.repul.subscription.domain.subscription.meal_kit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.fixture.subscription.MealKitFixture;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.SubscriberConfirmationStatus;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.exception.MealKitCancelledException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.exception.MealKitCannotBeConfirmedException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MealKitTest {

    private final MealKitFixture mealKitFixture = new MealKitFixture();
    private final LocalDate DELAY_EXPIRED_DELIVERY_DATE = LocalDate
        .now()
        .minusDays(mealKitFixture.confirmationDelayInDays - 1);
    private final LocalDate DELAY_RESPECTED_DELIVERY_DATE = LocalDate
        .now()
        .plusDays(mealKitFixture.confirmationDelayInDays + 1);
    private final MealKit MEAL_KIT = mealKitFixture
        .withDeliveryDate(DELAY_RESPECTED_DELIVERY_DATE)
        .build();
    private final MealKit DELAY_EXPIRED_MEAL_KIT = mealKitFixture
        .withDeliveryDate(DELAY_EXPIRED_DELIVERY_DATE)
        .build();
    private final MealKit ALREADY_PAID_MEAL_KIT = mealKitFixture
        .withSubscriberConfirmationStatus(SubscriberConfirmationStatus.PAID)
        .withDeliveryDate(DELAY_RESPECTED_DELIVERY_DATE)
        .build();
    private final MealKit REFUSED_MEAL_KIT = mealKitFixture
        .withSubscriberConfirmationStatus(SubscriberConfirmationStatus.REFUSED)
        .withDeliveryDate(DELAY_RESPECTED_DELIVERY_DATE)
        .build();

    @Test
    void getConfirmationDelayInDays_returnsConfirmationDelayInDays() {
        assertEquals(
            mealKitFixture.confirmationDelayInDays,
            MEAL_KIT.getConfirmationDelayInDays()
        );
    }

    @Test
    void refuse_changesStatusToRefused() {
        MEAL_KIT.refuse();

        assertEquals(
            SubscriberConfirmationStatus.REFUSED,
            MEAL_KIT.getSubscriberConfirmationStatus()
        );
    }

    @Test
    void confirm_changesStatusToPAID() {
        MEAL_KIT.confirm();

        SubscriberConfirmationStatus actualStatus =
            MEAL_KIT.getSubscriberConfirmationStatus();
        assertEquals(SubscriberConfirmationStatus.PAID, actualStatus);
    }

    @Test
    void confirm_delayExpired_throwsMealKitCancelledException() {
        assertThrows(
            MealKitCancelledException.class,
            () -> DELAY_EXPIRED_MEAL_KIT.confirm()
        );
    }

    @Test
    void confirm_alreadyConfirmed_throwsMealKitCannotBeConfirmedException() {
        assertThrows(
            MealKitCannotBeConfirmedException.class,
            () -> ALREADY_PAID_MEAL_KIT.confirm()
        );
    }

    @Test
    void confirm_alreadyRefused_throwsMealKitCannotBeConfirmedException() {
        assertThrows(
            MealKitCannotBeConfirmedException.class,
            () -> REFUSED_MEAL_KIT.confirm()
        );
    }
}

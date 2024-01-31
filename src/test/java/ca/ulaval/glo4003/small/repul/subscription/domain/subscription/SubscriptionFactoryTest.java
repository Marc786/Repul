package ca.ulaval.glo4003.small.repul.subscription.domain.subscription;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.lib.semester.registry.finder.SubscriptionSemesterFinder;
import ca.ulaval.glo4003.repul.subscription.application.exception.InvalidSubscriptionStartDateException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.SubscriptionFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionFactoryTest {

    private static final String INVALID_DELIVERY_DATE_STRING = "2020-01-01";
    private static final LocalDate INVALID_DELIVERY_DATE = LocalDate.parse(
        INVALID_DELIVERY_DATE_STRING
    );
    private static final DayOfWeek DELIVERING_DAY_OF_WEEK = DayOfWeek.MONDAY;
    private static final Frequency DELIVERY_FREQUENCY = Frequency.WEEKLY;
    private static final PickupPointLocation PICKUP_POINT_LOCATION =
        PickupPointLocation.VACHON;
    private static final MealKitType MEAL_KIT_TYPE = MealKitType.STANDARD;
    private final SubscriptionSemesterFinder subscriptionSemesterFinderMock = mock(
        SubscriptionSemesterFinder.class
    );
    private SubscriptionFactory subscriptionFactory;

    @BeforeEach
    public void setUpSubscriptionFactory() {
        subscriptionFactory = new SubscriptionFactory();
    }

    @Test
    void invalidDeliveryDate_createRecurringSubscription_shouldThrowInvalidDeliveryDateException() {
        when(
            subscriptionSemesterFinderMock.findCurrentOrNextSemester(
                INVALID_DELIVERY_DATE
            )
        )
            .thenThrow(
                new InvalidSubscriptionStartDateException(INVALID_DELIVERY_DATE_STRING)
            );

        assertThrows(
            InvalidSubscriptionStartDateException.class,
            () ->
                subscriptionFactory.createRecurring(
                    subscriptionSemesterFinderMock,
                    DELIVERING_DAY_OF_WEEK,
                    INVALID_DELIVERY_DATE,
                    DELIVERY_FREQUENCY,
                    PICKUP_POINT_LOCATION,
                    MEAL_KIT_TYPE
                )
        );
    }

    @Test
    void invalidDeliveryDate_createSporadicSubscription_shouldThrowInvalidDeliveryDateException() {
        when(
            subscriptionSemesterFinderMock.findCurrentOrNextSemester(
                INVALID_DELIVERY_DATE
            )
        )
            .thenThrow(
                new InvalidSubscriptionStartDateException(INVALID_DELIVERY_DATE_STRING)
            );

        assertThrows(
            InvalidSubscriptionStartDateException.class,
            () ->
                subscriptionFactory.createSporadic(
                    subscriptionSemesterFinderMock,
                    INVALID_DELIVERY_DATE,
                    DELIVERY_FREQUENCY,
                    PICKUP_POINT_LOCATION,
                    MEAL_KIT_TYPE
                )
        );
    }
}

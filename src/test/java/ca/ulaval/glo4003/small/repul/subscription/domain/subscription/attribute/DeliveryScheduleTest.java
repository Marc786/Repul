package ca.ulaval.glo4003.small.repul.subscription.domain.subscription.attribute;

import static ca.ulaval.glo4003.constant.Constants.Subscription.MEAL_KIT_PREPARATION_DELAY_IN_DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.fixture.subscription.DeliveryScheduleFixture;
import ca.ulaval.glo4003.repul.subscription.application.exception.InvalidDeliveryScheduleException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.DeliverySchedule;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DeliveryScheduleTest {

    private final Frequency WEEKLY_FREQUENCY = Frequency.WEEKLY;
    private final Frequency SPORADIC_FREQUENCY = Frequency.SPORADIC;
    private final LocalDate NEXT_TUESDAY = findNext(DayOfWeek.TUESDAY);
    private final DeliveryScheduleFixture deliveryScheduleFixture =
        new DeliveryScheduleFixture();
    private final DeliverySchedule SPORADIC_DELIVERY_SCHEDULE = deliveryScheduleFixture
        .withFrequency(SPORADIC_FREQUENCY)
        .build();
    private final DeliverySchedule FRIDAY_WEEKLY_DELIVERY_SCHEDULE =
        deliveryScheduleFixture
            .withFrequency(WEEKLY_FREQUENCY)
            .withStartDate(NEXT_TUESDAY)
            .withEndDate(NEXT_TUESDAY.plusMonths(1))
            .withDeliveryDay(DayOfWeek.FRIDAY)
            .build();
    private final DeliverySchedule ALREADY_STARTED_DELIVERY_SCHEDULE =
        deliveryScheduleFixture
            .withFrequency(WEEKLY_FREQUENCY)
            .withStartDate(NEXT_TUESDAY.minusDays(7))
            .withEndDate(NEXT_TUESDAY.plusMonths(1))
            .withDeliveryDay(DayOfWeek.FRIDAY)
            .build();
    private final DeliverySchedule FINISHED_DELIVERY_SCHEDULE = deliveryScheduleFixture
        .withFrequency(WEEKLY_FREQUENCY)
        .withStartDate(NEXT_TUESDAY.minusDays(14))
        .withEndDate(NEXT_TUESDAY.minusDays(7))
        .withDeliveryDay(DayOfWeek.TUESDAY)
        .build();

    @Test
    void sporadicFrequency_getNextDeliveryDate_returnsDateInTwoDays() {
        LocalDate expectedDeliveryDate = LocalDate
            .now()
            .plusDays(MEAL_KIT_PREPARATION_DELAY_IN_DAYS);

        LocalDate actualDeliveryDate = SPORADIC_DELIVERY_SCHEDULE.getNextDeliveryDate();

        assertEquals(expectedDeliveryDate, actualDeliveryDate);
    }

    @Test
    void startDateAfterCurrentDate_getNextDeliveryDate_returnsNextDayOfWeekAfterStartDate() {
        LocalDate nextFriday = FRIDAY_WEEKLY_DELIVERY_SCHEDULE.getStartDate().plusDays(3);

        LocalDate actualDeliveryDate =
            FRIDAY_WEEKLY_DELIVERY_SCHEDULE.getNextDeliveryDate();

        assertEquals(nextFriday, actualDeliveryDate);
    }

    @Test
    void startDateBeforeCurrentDate_getNextDeliveryDate_returnsNextDayOfWeek() {
        LocalDate nextFriday = findNext(DayOfWeek.FRIDAY);

        LocalDate actualDeliveryDate =
            ALREADY_STARTED_DELIVERY_SCHEDULE.getNextDeliveryDate();

        assertEquals(nextFriday, actualDeliveryDate);
    }

    @Test
    void nextDeliveryDateIsAfterEndDate_getNextDeliveryDate_returnsNextDayOfWeek() {
        assertThrows(
            InvalidDeliveryScheduleException.class,
            () -> FINISHED_DELIVERY_SCHEDULE.getNextDeliveryDate()
        );
    }

    private LocalDate findNext(DayOfWeek dayOfWeek) {
        LocalDate date = LocalDate.now();
        while (date.getDayOfWeek() != dayOfWeek) {
            date = date.plusDays(1);
        }
        return date;
    }
}

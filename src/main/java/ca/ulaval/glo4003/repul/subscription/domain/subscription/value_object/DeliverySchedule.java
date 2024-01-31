package ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup;
import static ca.ulaval.glo4003.constant.Constants.Subscription.MEAL_KIT_PREPARATION_DELAY_IN_DAYS;

import ca.ulaval.glo4003.repul.subscription.application.exception.InvalidDeliveryScheduleException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public final class DeliverySchedule {

    public static final String DELIVERY_DATE_AFTER_END_DATE =
        "Delivery date cannot be after end date";
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Frequency frequency;
    private DayOfWeek deliveryDayOfWeek;

    public DeliverySchedule(
        DayOfWeek deliveryDayOfWeek,
        LocalDate startDate,
        LocalDate endDate,
        Frequency frequency
    ) {
        this.deliveryDayOfWeek = deliveryDayOfWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
    }

    public DeliverySchedule(LocalDate startDate, LocalDate endDate, Frequency frequency) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.frequency = frequency;
    }

    public LocalDate getNextDeliveryDate() {
        LocalDate currentDate = LocalDate.now(ClockSetup.getClock());
        LocalDate nextDeliveryDate;

        if (frequency == Frequency.SPORADIC) {
            return generateDateWithMinimumDelay();
        }

        if (startDate.isAfter(currentDate)) {
            nextDeliveryDate = findNextDateWithDayOfWeek(startDate, deliveryDayOfWeek);
        } else {
            nextDeliveryDate = findNextDateWithDayOfWeek(currentDate, deliveryDayOfWeek);
        }

        if (nextDeliveryDate.isAfter(endDate)) {
            throw new InvalidDeliveryScheduleException(DELIVERY_DATE_AFTER_END_DATE);
        }

        return nextDeliveryDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public DayOfWeek getDeliveryDayOfWeek() {
        return deliveryDayOfWeek;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    private LocalDate generateDateWithMinimumDelay() {
        return LocalDate
            .now(ClockSetup.getClock())
            .plusDays(MEAL_KIT_PREPARATION_DELAY_IN_DAYS);
    }

    private LocalDate findNextDateWithDayOfWeek(LocalDate date, DayOfWeek dayOfWeek) {
        while (date.getDayOfWeek() != dayOfWeek) {
            date = date.plusDays(1);
        }
        return date;
    }
}

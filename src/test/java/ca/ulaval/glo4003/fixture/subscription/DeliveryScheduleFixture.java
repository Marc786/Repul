package ca.ulaval.glo4003.fixture.subscription;

import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.DeliverySchedule;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class DeliveryScheduleFixture {

    public DayOfWeek deliveryDay = DayOfWeek.MONDAY;
    public LocalDate startDate = LocalDate.of(2024, 1, 1);
    public Frequency frequency = Frequency.WEEKLY;
    public LocalDate endDate = LocalDate.of(2024, 4, 1);

    public DeliverySchedule build() {
        return new DeliverySchedule(deliveryDay, startDate, endDate, frequency);
    }

    public DeliveryScheduleFixture withFrequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public DeliveryScheduleFixture withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public DeliveryScheduleFixture withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public DeliveryScheduleFixture withDeliveryDay(DayOfWeek deliveryDay) {
        this.deliveryDay = deliveryDay;
        return this;
    }
}

package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.repul.subscription.api.dto.request.CreateSubscriptionRequest;
import java.time.DayOfWeek;

public class CreateSubscriptionRequestFixture {

    private String startDate = "2021-09-06";
    private String location = "DESJARDINS";
    private final String mealKitType = "standard";
    private final DayOfWeek deliveringDayOfWeek = DayOfWeek.MONDAY;
    private final String frequency = "weekly";

    public CreateSubscriptionRequest build() {
        return new CreateSubscriptionRequest(
            frequency,
            location,
            mealKitType,
            startDate,
            deliveringDayOfWeek
        );
    }

    public CreateSubscriptionRequestFixture withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public CreateSubscriptionRequestFixture withLocation(String location) {
        this.location = location;
        return this;
    }
}

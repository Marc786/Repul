package ca.ulaval.glo4003.repul.subscription.domain.subscription;

import ca.ulaval.glo4003.constant.Constants.ClockSetup;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.exception.SemesterNotFoundException;
import ca.ulaval.glo4003.lib.semester.registry.finder.SubscriptionSemesterFinder;
import ca.ulaval.glo4003.repul.subscription.application.exception.InvalidSubscriptionStartDateException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.RecurringMealKitFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.SporadicMealKitFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.DeliverySchedule;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class SubscriptionFactory {

    public Subscription createRecurring(
        SubscriptionSemesterFinder subscriptionSemesterFinder,
        DayOfWeek deliveringDayOfWeek,
        LocalDate deliveryStartDate,
        Frequency deliveryFrequency,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType
    ) {
        if (deliveryStartDate.isBefore(LocalDate.now(ClockSetup.getClock()))) {
            throw new InvalidSubscriptionStartDateException(deliveryStartDate.toString());
        }

        Semester semester = findSemester(subscriptionSemesterFinder, deliveryStartDate);

        DeliverySchedule deliverySchedule = new DeliverySchedule(
            deliveringDayOfWeek,
            deliveryStartDate,
            semester.endDate(),
            deliveryFrequency
        );

        MealKitFactory mealKitFactory = new RecurringMealKitFactory();

        return new Subscription(
            new SubscriptionId(),
            deliverySchedule,
            pickupPointLocation,
            mealKitType,
            mealKitFactory
        );
    }

    public Subscription createSporadic(
        SubscriptionSemesterFinder subscriptionSemesterFinder,
        LocalDate deliveryStartDate,
        Frequency deliveryFrequency,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType
    ) {
        Semester semester = findSemester(subscriptionSemesterFinder, deliveryStartDate);

        DeliverySchedule deliverySchedule = new DeliverySchedule(
            deliveryStartDate,
            semester.endDate(),
            deliveryFrequency
        );

        MealKitFactory mealKitFactory = new SporadicMealKitFactory();

        return new Subscription(
            new SubscriptionId(),
            deliverySchedule,
            pickupPointLocation,
            mealKitType,
            mealKitFactory
        );
    }

    private Semester findSemester(
        SubscriptionSemesterFinder subscriptionSemesterFinder,
        LocalDate deliveryStartDate
    ) {
        Semester semester;
        try {
            semester =
                subscriptionSemesterFinder.findCurrentOrNextSemester(deliveryStartDate);
        } catch (SemesterNotFoundException e) {
            throw new InvalidSubscriptionStartDateException(deliveryStartDate.toString());
        }
        return semester;
    }
}

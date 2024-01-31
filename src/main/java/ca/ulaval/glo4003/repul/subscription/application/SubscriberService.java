package ca.ulaval.glo4003.repul.subscription.application;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup;
import static ca.ulaval.glo4003.constant.Constants.Kitchen.KITCHEN_PICKUP_POINT_LOCATION;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.lib.semester.registry.finder.SubscriptionSemesterFinder;
import ca.ulaval.glo4003.repul.subscription.domain.BillClient;
import ca.ulaval.glo4003.repul.subscription.domain.MealClient;
import ca.ulaval.glo4003.repul.subscription.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.subscription.domain.Subscriber;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberRepository;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.SubscriptionFactory;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.exception.SubscriptionDoesNotBelongToSubscriberException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import ca.ulaval.glo4003.repul.subscription.infra.exception.SubscriberNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class SubscriberService {

    private final SubscriptionFactory subscriptionFactory;
    private final SubscriberRepository subscriberRepository;
    private final SubscriptionSemesterFinder subscriptionSemesterFinder;
    private final BillClient billClient;
    private final MealClient mealClient;
    private final ShipmentClient shipmentClient;

    public SubscriberService(
        SubscriptionFactory subscriptionFactory,
        SubscriberRepository subscriberRepository,
        SubscriptionSemesterFinder subscriptionSemesterFinder,
        BillClient billClient,
        MealClient mealClient,
        ShipmentClient shipmentClient
    ) {
        this.subscriptionFactory = subscriptionFactory;
        this.subscriberRepository = subscriberRepository;
        this.subscriptionSemesterFinder = subscriptionSemesterFinder;
        this.billClient = billClient;
        this.mealClient = mealClient;
        this.shipmentClient = shipmentClient;
    }

    public SubscriptionId addRecurringSubscription(
        SubscriberId subscriberId,
        DayOfWeek deliveringDayOfWeek,
        LocalDate deliveryStartDate,
        Frequency deliveryFrequency,
        PickupPointLocation pickupPointLocation,
        MealKitType mealKitType
    ) {
        Subscriber subscriber = findOrCreateSubscriber(subscriberId);

        Subscription subscription = subscriptionFactory.createRecurring(
            subscriptionSemesterFinder,
            deliveringDayOfWeek,
            deliveryStartDate,
            deliveryFrequency,
            pickupPointLocation,
            mealKitType
        );

        subscriber.addSubscription(subscription);

        subscriberRepository.save(subscriber);

        return subscription.getId();
    }

    public SubscriptionId addSporadicSubscription(SubscriberId subscriberId) {
        Subscriber subscriber = findOrCreateSubscriber(subscriberId);

        Subscription subscription = subscriptionFactory.createSporadic(
            subscriptionSemesterFinder,
            LocalDate.now(ClockSetup.getClock()),
            Frequency.SPORADIC,
            KITCHEN_PICKUP_POINT_LOCATION,
            MealKitType.STANDARD
        );

        subscriber.addSubscription(subscription);

        subscriberRepository.save(subscriber);
        return subscription.getId();
    }

    public List<Subscription> getSubscriptions(SubscriberId subscriberId) {
        try {
            return subscriberRepository.findById(subscriberId).getSubscriptions();
        } catch (SubscriberNotFoundException e) {
            return List.of();
        }
    }

    public void confirmMealKit(
        SubscriberId subscriberId,
        SubscriptionId subscriptionId,
        boolean isAccepted
    ) {
        Subscriber subscriber;
        try {
            subscriber = subscriberRepository.findById(subscriberId);
        } catch (SubscriberNotFoundException e) {
            throw new SubscriptionDoesNotBelongToSubscriberException(subscriptionId);
        }

        subscriber.generateNextMealKit(subscriptionId);
        confirm(subscriptionId, isAccepted, subscriber);

        subscriber.addConfirmedMealKitShipmentItem(subscriptionId, shipmentClient);

        subscriberRepository.save(subscriber);
    }

    public List<MealKit> getMealKits(SubscriberId subscriberId) {
        try {
            Subscriber subscriber = subscriberRepository.findById(subscriberId);
            return subscriber.getMealKits();
        } catch (SubscriberNotFoundException e) {
            return List.of();
        }
    }

    private void confirm(
        SubscriptionId subscriptionId,
        boolean isAccepted,
        Subscriber subscriber
    ) {
        if (isAccepted) {
            subscriber.confirmNextMealKit(subscriptionId, billClient, mealClient);
        } else {
            subscriber.refuseNextMealKit(subscriptionId);
        }
    }

    private Subscriber findOrCreateSubscriber(SubscriberId subscriberId) {
        Subscriber subscriber;
        try {
            subscriber = subscriberRepository.findById(subscriberId);
        } catch (SubscriberNotFoundException e) {
            subscriber = new Subscriber(subscriberId);
        }
        return subscriber;
    }
}

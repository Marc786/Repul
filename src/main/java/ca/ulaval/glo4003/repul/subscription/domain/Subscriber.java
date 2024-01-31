package ca.ulaval.glo4003.repul.subscription.domain;

import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.exception.SubscriptionDoesNotBelongToSubscriberException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Subscriber {

    private final SubscriberId id;
    private final List<Subscription> subscriptions;

    public Subscriber(SubscriberId id) {
        this.id = id;
        this.subscriptions = new ArrayList<>();
    }

    public Subscriber(SubscriberId id, List<Subscription> subscriptions) {
        this.id = id;
        this.subscriptions = subscriptions;
    }

    public SubscriberId getId() {
        return id;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public List<MealKit> getMealKits() {
        return subscriptions
            .stream()
            .map(Subscription::getMealKits)
            .flatMap(List::stream)
            .toList();
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void confirmNextMealKit(
        SubscriptionId subscriptionId,
        BillClient billClient,
        MealClient mealClient
    ) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        billNextMealKit(subscriptionId, billClient);
        subscription.confirmNextMealKit();
        addMealToPrepare(subscriptionId, mealClient);
    }

    public void refuseNextMealKit(SubscriptionId subscriptionId) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        subscription.refuseNextMealKit();
    }

    public void generateNextMealKit(SubscriptionId subscriptionId) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        subscription.generateNextMealKit();
    }

    public void addMealToPrepare(SubscriptionId subscriptionId, MealClient mealClient) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        MealKit mealKit = subscription.getNextMealKit();

        mealClient.addMealToPrepare(mealKit);
    }

    public void addConfirmedMealKitShipmentItem(
        SubscriptionId subscriptionId,
        ShipmentClient shipmentClient
    ) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        MealKit mealKit = subscription.getNextMealKit();

        shipmentClient.addConfirmedMealKitShipment(mealKit);
    }

    private void billNextMealKit(SubscriptionId subscriptionId, BillClient billClient) {
        Subscription subscription = findSubscriptionById(subscriptionId);
        MealKit mealKit = subscription.getNextMealKit();

        billClient.bill(id, mealKit);
    }

    private Subscription findSubscriptionById(SubscriptionId subscriptionId) {
        return subscriptions
            .stream()
            .filter(sub -> sub.getId().equals(subscriptionId))
            .findFirst()
            .orElseThrow(() ->
                new SubscriptionDoesNotBelongToSubscriberException(subscriptionId)
            );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Subscriber that = (Subscriber) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

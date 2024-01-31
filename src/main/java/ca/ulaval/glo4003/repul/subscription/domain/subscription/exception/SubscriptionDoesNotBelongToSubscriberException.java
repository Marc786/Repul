package ca.ulaval.glo4003.repul.subscription.domain.subscription.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;

public class SubscriptionDoesNotBelongToSubscriberException
    extends InvalidInputException {

    public static final String ERROR_MESSAGE =
        "Subscription with the id %s does not belong to subscriber";

    public SubscriptionDoesNotBelongToSubscriberException(SubscriptionId subscriptionId) {
        super(String.format(ERROR_MESSAGE, subscriptionId));
    }
}

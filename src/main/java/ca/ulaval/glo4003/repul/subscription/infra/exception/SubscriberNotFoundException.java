package ca.ulaval.glo4003.repul.subscription.infra.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;

public class SubscriberNotFoundException extends ItemNotFoundException {

    public SubscriberNotFoundException(SubscriberId id) {
        super(String.format("Subscriber with id %s not found", id));
    }
}

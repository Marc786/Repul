package ca.ulaval.glo4003.repul.subscription.infra.cloner;

import ca.ulaval.glo4003.repul.subscription.domain.Subscriber;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import com.rits.cloning.Cloner;
import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.util.Map;

public class SubscriberFastCloner implements IFastCloner {

    private final Cloner deepCloner = new Cloner();

    public SubscriberFastCloner() {
        deepCloner.registerFastCloner(Subscription.class, new SubscriptionFastCloner());
    }

    public Object clone(
        final Object t,
        final IDeepCloner cloner,
        final Map<Object, Object> clones
    ) {
        final Subscriber original = (Subscriber) t;

        return new Subscriber(
            new SubscriberId(original.getId().toString()),
            deepCloner.deepClone(original.getSubscriptions())
        );
    }
}

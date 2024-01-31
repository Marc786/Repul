package ca.ulaval.glo4003.repul.subscription.api.assembler;

import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionResponse;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionsResponse;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import java.util.List;

public class SubscriptionResponseAssembler {

    public SubscriptionsResponse toResponse(List<Subscription> subscriptions) {
        return new SubscriptionsResponse(
            subscriptions.stream().map(this::toSubscriptionResponse).toList()
        );
    }

    private SubscriptionResponse toSubscriptionResponse(Subscription subscription) {
        return new SubscriptionResponse(
            subscription.getId().toString(),
            subscription.getFrequency().toString(),
            subscription.getPickupPointLocation().toString(),
            subscription.getMealKitType().toString(),
            subscription.getStartDate().toString(),
            subscription.getEndDate().toString()
        );
    }
}

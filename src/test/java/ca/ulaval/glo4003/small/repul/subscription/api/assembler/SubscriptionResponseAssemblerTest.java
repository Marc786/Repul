package ca.ulaval.glo4003.small.repul.subscription.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.subscription.SubscriptionFixture;
import ca.ulaval.glo4003.repul.subscription.api.assembler.SubscriptionResponseAssembler;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionResponse;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionsResponse;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import java.util.List;
import org.junit.jupiter.api.Test;

class SubscriptionResponseAssemblerTest {

    private final SubscriptionFixture subscriptionFixture = new SubscriptionFixture();
    private final Subscription subscription = subscriptionFixture.build();
    private final Subscription otherSubscription = subscriptionFixture.withId().build();
    private final List<Subscription> subscriptions = List.of(
        subscription,
        otherSubscription
    );
    private final SubscriptionResponseAssembler subscriptionResponseAssembler =
        new SubscriptionResponseAssembler();

    @Test
    void toResponse_returnsSubscriptionsResponse() {
        SubscriptionsResponse expectedSubscriptionsResponse = new SubscriptionsResponse(
            List.of(
                new SubscriptionResponse(
                    subscription.getId().toString(),
                    subscription.getFrequency().toString(),
                    subscription.getPickupPointLocation().toString(),
                    subscription.getMealKitType().toString(),
                    subscription.getStartDate().toString(),
                    subscription.getEndDate().toString()
                ),
                new SubscriptionResponse(
                    otherSubscription.getId().toString(),
                    otherSubscription.getFrequency().toString(),
                    otherSubscription.getPickupPointLocation().toString(),
                    otherSubscription.getMealKitType().toString(),
                    otherSubscription.getStartDate().toString(),
                    otherSubscription.getEndDate().toString()
                )
            )
        );

        SubscriptionsResponse actualSubscriptionResponse =
            subscriptionResponseAssembler.toResponse(subscriptions);

        assertEquals(expectedSubscriptionsResponse, actualSubscriptionResponse);
    }
}

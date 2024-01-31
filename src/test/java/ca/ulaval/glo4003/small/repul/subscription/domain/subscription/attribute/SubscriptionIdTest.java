package ca.ulaval.glo4003.small.repul.subscription.domain.subscription.attribute;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class SubscriptionIdTest {

    private final String UUID_STRING = UUID.randomUUID().toString();

    @Test
    void twoNewSubscriptionId_shouldGenerateDifferentUUID() {
        SubscriptionId subscriptionId = new SubscriptionId();
        SubscriptionId otherSubscriptionId = new SubscriptionId();

        assertNotEquals(subscriptionId, otherSubscriptionId);
    }

    @Test
    void fromString_returnsWithSameUUID() {
        SubscriptionId subscriptionId = new SubscriptionId(UUID_STRING);

        assertEquals(UUID_STRING, subscriptionId.toString());
    }
}

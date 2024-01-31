package ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object;

import java.util.UUID;

public class SubscriptionId {

    private final UUID value;

    public SubscriptionId() {
        this.value = UUID.randomUUID();
    }

    public SubscriptionId(String value) {
        this.value = UUID.fromString(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SubscriptionId subscriptionId = (SubscriptionId) obj;

        return value.equals(subscriptionId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

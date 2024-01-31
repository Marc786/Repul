package ca.ulaval.glo4003.repul.account.domain.customer.value_object;

import ca.ulaval.glo4003.lib.value_object.AccountId;

public record CustomerProfileId(String id) implements AccountId {
    @Override
    public String toString() {
        return id;
    }
}

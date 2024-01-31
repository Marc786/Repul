package ca.ulaval.glo4003.event.account.customer;

import ca.ulaval.glo4003.lib.value_object.AccountId;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;

public record CustomerCreatedEvent(
    AccountId accountId,
    EmailAddress email,
    Password password
) {}

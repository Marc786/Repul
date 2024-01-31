package ca.ulaval.glo4003.event.account.cook;

import ca.ulaval.glo4003.lib.value_object.AccountId;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;

public record CookCreatedEvent(
    AccountId accountId,
    EmailAddress email,
    Password password
) {}

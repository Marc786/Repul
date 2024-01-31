package ca.ulaval.glo4003.event.account.carrier;

import ca.ulaval.glo4003.lib.value_object.AccountId;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;

public record CarrierCreatedEvent(
    AccountId accountId,
    EmailAddress email,
    Password password
) {}

package ca.ulaval.glo4003.repul.payment.domain;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CreditCard;

public interface CreditCardProcessor {
    void processPayment(CreditCard creditCard, Amount amount);
}

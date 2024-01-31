package ca.ulaval.glo4003.repul.payment.infra;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.payment.domain.CreditCardProcessor;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CreditCard;

public class StripeApi implements CreditCardProcessor {

    @Override
    public void processPayment(CreditCard creditCard, Amount amount) {
        // payment processing logic
    }
}

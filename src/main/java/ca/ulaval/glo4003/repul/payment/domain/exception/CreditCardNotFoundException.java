package ca.ulaval.glo4003.repul.payment.domain.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;

public class CreditCardNotFoundException extends ItemNotFoundException {

    public static final String CREDIT_CARD_NOT_FOUND =
        "Credit card not found, please enter one";

    public CreditCardNotFoundException() {
        super(CREDIT_CARD_NOT_FOUND);
    }
}

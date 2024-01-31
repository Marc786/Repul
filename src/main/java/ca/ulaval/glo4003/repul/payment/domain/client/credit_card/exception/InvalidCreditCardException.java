package ca.ulaval.glo4003.repul.payment.domain.client.credit_card.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidCreditCardException extends InvalidInputException {

    public InvalidCreditCardException(String message) {
        super(message);
    }
}

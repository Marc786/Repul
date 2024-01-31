package ca.ulaval.glo4003.repul.payment.domain.client.credit_card;

import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.exception.InvalidCreditCardException;

public class CardNumber {

    private static final String INVALID_CARD_NUMBER_MSG = "Credit card number is invalid";
    private static final String CARD_NUMBER_FORMAT = "^(\\d{16})$";
    private final String value;

    public CardNumber(String value) {
        this.value = value;
    }

    public void validate() {
        if (!value.matches(CARD_NUMBER_FORMAT)) {
            throw new InvalidCreditCardException(INVALID_CARD_NUMBER_MSG);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CardNumber cardNumber)) {
            return false;
        }
        return value.equals(cardNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

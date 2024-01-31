package ca.ulaval.glo4003.repul.payment.domain.client.credit_card;

import static ca.ulaval.glo4003.constant.Constants.Payment.CCV_FORMAT;
import static ca.ulaval.glo4003.constant.Constants.Payment.INVALID_CCV_MSG;

import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.exception.InvalidCreditCardException;

public class Ccv {

    private final String value;

    public Ccv(String value) {
        this.value = value;
    }

    public void validate() {
        if (!value.matches(CCV_FORMAT)) {
            throw new InvalidCreditCardException(INVALID_CCV_MSG);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Ccv ccv)) {
            return false;
        }
        return value.equals(ccv.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

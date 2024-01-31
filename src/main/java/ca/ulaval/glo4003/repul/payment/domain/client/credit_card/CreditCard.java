package ca.ulaval.glo4003.repul.payment.domain.client.credit_card;

import java.util.Objects;

public class CreditCard {

    private final CardNumber creditCardNumber;
    private final CardExpirationDate creditCardExpirationDate;
    private final Ccv creditCardCcv;

    public CreditCard(
        CardNumber creditCardNumber,
        CardExpirationDate creditCardExpirationDate,
        Ccv creditCardCcv
    ) {
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpirationDate = creditCardExpirationDate;
        this.creditCardCcv = creditCardCcv;
    }

    public void validate() {
        creditCardExpirationDate.validate();
        creditCardNumber.validate();
        creditCardCcv.validate();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CreditCard creditCard)) {
            return false;
        }
        return (
            Objects.equals(creditCardNumber, creditCard.creditCardNumber) &&
            Objects.equals(
                creditCardExpirationDate,
                creditCard.creditCardExpirationDate
            ) &&
            Objects.equals(creditCardCcv, creditCard.creditCardCcv)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditCardNumber, creditCardExpirationDate, creditCardCcv);
    }
}

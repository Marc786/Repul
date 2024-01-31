package ca.ulaval.glo4003.repul.payment.domain.client.credit_card;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup;

import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.exception.InvalidCreditCardException;
import java.time.LocalDate;
import java.time.Month;

public class CardExpirationDate {

    private static final String INVALID_EXPIRATION_DATE_MSG =
        "Credit card expiration date is invalid";
    private static final String WRONG_FORMAT_EXPIRATION_DATE_MSG =
        "Wrong format for credit card expiration date";
    private static final String EXPIRATION_DATE_FORMAT = "\\d{2}-\\d{4}";
    private final String expirationDate;

    public CardExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void validate() {
        if (!expirationDate.matches(EXPIRATION_DATE_FORMAT)) {
            throw new InvalidCreditCardException(WRONG_FORMAT_EXPIRATION_DATE_MSG);
        }
        LocalDate expirationLocalDate = getExpirationConvertedToLocalDate();
        if (expirationLocalDate.isBefore(LocalDate.now(ClockSetup.getClock()))) {
            throw new InvalidCreditCardException(INVALID_EXPIRATION_DATE_MSG);
        }
    }

    private LocalDate getExpirationConvertedToLocalDate() {
        String[] dateParts = expirationDate.split("-");
        int year = Integer.parseInt(dateParts[1]);
        int month = Integer.parseInt(dateParts[0]);
        return LocalDate.of(year, Month.of(month), 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CardExpirationDate cardExpirationDate)) {
            return false;
        }
        return expirationDate.equals(cardExpirationDate.expirationDate);
    }

    @Override
    public int hashCode() {
        return expirationDate.hashCode();
    }
}

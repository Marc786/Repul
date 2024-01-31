package ca.ulaval.glo4003.small.repul.payment.domain.client.credit_card;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardExpirationDate;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.exception.InvalidCreditCardException;
import java.util.Calendar;
import org.junit.jupiter.api.Test;

class CardExpirationDateTest {

    private static final int NEXT_YEAR = Calendar.getInstance().get(Calendar.YEAR) + 1;
    private static final String VALID_DATE = String.format("12-%d", NEXT_YEAR);
    private static final String EXPIRED_DATE = "12-2020";
    private static final String WRONG_FORMAT_DATE = String.format("12/%d", NEXT_YEAR);

    @Test
    void validDate_validate_noExceptionThrown() {
        CardExpirationDate cardExpirationDateValid = new CardExpirationDate(VALID_DATE);

        assertDoesNotThrow(cardExpirationDateValid::validate);
    }

    @Test
    void dateIsExpired_validate_throwInvalidCreditCardException() {
        CardExpirationDate cardExpirationDateExpired = new CardExpirationDate(
            EXPIRED_DATE
        );

        assertThrows(
            InvalidCreditCardException.class,
            cardExpirationDateExpired::validate
        );
    }

    @Test
    void wrongFormatDate_createCardExpirationDate_throwInvalidCreditCardException() {
        CardExpirationDate cardExpirationDateWrongFormat = new CardExpirationDate(
            WRONG_FORMAT_DATE
        );

        assertThrows(
            InvalidCreditCardException.class,
            cardExpirationDateWrongFormat::validate
        );
    }
}

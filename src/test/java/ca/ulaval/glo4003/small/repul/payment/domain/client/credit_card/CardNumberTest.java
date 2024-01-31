package ca.ulaval.glo4003.small.repul.payment.domain.client.credit_card;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardNumber;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.exception.InvalidCreditCardException;
import org.junit.jupiter.api.Test;

class CardNumberTest {

    private final String VALID_NUMBER = "1234567890123456";
    private final String NUMBER_WRONG_LENGTH = "123456789012345";
    private final String NUMBER_NOT_ONLY_DIGITS = "123456789012345A";

    @Test
    void validNumber_validate_noExceptionThrown() {
        CardNumber validCardNumber = new CardNumber(VALID_NUMBER);

        assertDoesNotThrow(validCardNumber::validate);
    }

    @Test
    void numberWrongLength_validate_throwInvalidCreditCardException() {
        CardNumber invalidCardNumber = new CardNumber(NUMBER_WRONG_LENGTH);

        assertThrows(InvalidCreditCardException.class, invalidCardNumber::validate);
    }

    @Test
    void numberNotOnlyDigits_validate_throwInvalidCreditCardException() {
        CardNumber invalidCardNumber = new CardNumber(NUMBER_NOT_ONLY_DIGITS);

        assertThrows(InvalidCreditCardException.class, invalidCardNumber::validate);
    }
}

package ca.ulaval.glo4003.small.repul.payment.domain.client.credit_card;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.Ccv;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.exception.InvalidCreditCardException;
import org.junit.jupiter.api.Test;

class CcvTest {

    private static final String VALID_CCV = "123";
    private static final String NOT_ONLY_DIGITS_CCV = "A23";
    private static final String MORE_THAN_3_DIGITS_CCV = "1234";
    private static final String LESS_THAN_3_DIGITS_CCV = "12";

    @Test
    void validCcv_validate_noExceptionThrown() {
        Ccv validCcv = new Ccv(VALID_CCV);

        assertDoesNotThrow(validCcv::validate);
    }

    @Test
    void notOnlyDigitsCcv_validate_throwInvalidCreditCardException() {
        Ccv notOnlyDigitsCcv = new Ccv(NOT_ONLY_DIGITS_CCV);

        assertThrows(InvalidCreditCardException.class, notOnlyDigitsCcv::validate);
    }

    @Test
    void moreThan3DigitsCcv_validate_throwInvalidCreditCardException() {
        Ccv moreThan3DigitsCcv = new Ccv(MORE_THAN_3_DIGITS_CCV);

        assertThrows(InvalidCreditCardException.class, moreThan3DigitsCcv::validate);
    }

    @Test
    void lessThan3DigitsCcv_validate_throwInvalidCreditCardException() {
        Ccv lessThan3DigitsCcv = new Ccv(LESS_THAN_3_DIGITS_CCV);

        assertThrows(InvalidCreditCardException.class, lessThan3DigitsCcv::validate);
    }
}

package ca.ulaval.glo4003.small.repul.payment.domain.client.credit_card;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardExpirationDate;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardNumber;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.Ccv;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardTest {

    private final CardNumber cardNumber = mock(CardNumber.class);
    private final CardExpirationDate cardExpirationDate = mock(CardExpirationDate.class);
    private final Ccv ccv = mock(Ccv.class);

    private CreditCard creditCard;

    @BeforeEach
    void setUp() {
        creditCard = new CreditCard(cardNumber, cardExpirationDate, ccv);
    }

    @Test
    void validate_validatesCardNumber() {
        creditCard.validate();

        verify(cardNumber).validate();
    }

    @Test
    void validate_validatesCardExpirationDate() {
        creditCard.validate();

        verify(cardExpirationDate).validate();
    }

    @Test
    void validate_validatesCcv() {
        creditCard.validate();

        verify(ccv).validate();
    }
}

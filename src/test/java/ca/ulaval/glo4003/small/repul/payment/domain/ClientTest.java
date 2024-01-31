package ca.ulaval.glo4003.small.repul.payment.domain;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.payment.domain.CreditCardProcessor;
import ca.ulaval.glo4003.repul.payment.domain.client.Client;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CreditCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {

    private static final ClientId CUSTOMER_ID = new ClientId("123");
    private static final Amount AMOUNT = new Amount(123);
    private final CreditCard creditCard = mock(CreditCard.class);
    private final CreditCardProcessor creditCardProcessor = mock(
        CreditCardProcessor.class
    );
    private Client client;

    @BeforeEach
    void setup() {
        client = new Client(CUSTOMER_ID, creditCard);
    }

    @Test
    void pay_validateIsCalled() {
        client.pay(AMOUNT, creditCardProcessor);

        verify(creditCard).validate();
    }
}

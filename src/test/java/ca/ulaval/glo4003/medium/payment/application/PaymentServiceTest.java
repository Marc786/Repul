package ca.ulaval.glo4003.medium.payment.application;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.payment.application.PaymentService;
import ca.ulaval.glo4003.repul.payment.domain.ClientRepository;
import ca.ulaval.glo4003.repul.payment.domain.CreditCardProcessor;
import ca.ulaval.glo4003.repul.payment.domain.client.Client;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientFactory;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardExpirationDate;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardNumber;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.Ccv;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CreditCard;
import ca.ulaval.glo4003.repul.payment.domain.exception.CreditCardNotFoundException;
import ca.ulaval.glo4003.repul.payment.infra.InMemoryClientRepository;
import ca.ulaval.glo4003.repul.payment.infra.StripeApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

    private static final Amount AMOUNT = new Amount(100);
    private final ClientId CLIENT_ID = new ClientId("clientId");
    private final CardNumber CARD_NUMBER = new CardNumber("1234567812345678");
    private final CardExpirationDate CARD_EXPIRATION_DATE = new CardExpirationDate(
        "12-2099"
    );
    private final Ccv CCV = new Ccv("123");
    private final CreditCard CREDIT_CARD = new CreditCard(
        CARD_NUMBER,
        CARD_EXPIRATION_DATE,
        CCV
    );
    private final CreditCardProcessor creditCardProcessorMock = mock(
        CreditCardProcessor.class
    );
    private ClientRepository clientRepository;
    private ClientFactory clientFactory;
    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        clientRepository = new InMemoryClientRepository();
        CreditCardProcessor creditCardProcessor = new StripeApi();
        clientFactory = new ClientFactory();
        paymentService =
            new PaymentService(clientRepository, creditCardProcessor, clientFactory);
    }

    @Test
    void createClient_clientCreatedAndSavedToRepo() {
        paymentService.createClient(CLIENT_ID, CARD_NUMBER, CARD_EXPIRATION_DATE, CCV);

        Client client = clientRepository.findById(CLIENT_ID);
        assertEquals(CLIENT_ID, client.getId());
    }

    @Test
    void pay_processPaymentWithCreditCardAndAmount() {
        paymentService =
            new PaymentService(clientRepository, creditCardProcessorMock, clientFactory);
        paymentService.createClient(CLIENT_ID, CARD_NUMBER, CARD_EXPIRATION_DATE, CCV);

        paymentService.pay(CLIENT_ID, AMOUNT);

        verify(creditCardProcessorMock).processPayment(eq(CREDIT_CARD), eq(AMOUNT));
    }

    @Test
    void pay_clientNotFound_throwsException() {
        assertThrows(
            CreditCardNotFoundException.class,
            () -> paymentService.pay(CLIENT_ID, AMOUNT)
        );
    }
}

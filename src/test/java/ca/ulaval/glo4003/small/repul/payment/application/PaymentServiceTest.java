package ca.ulaval.glo4003.small.repul.payment.application;

import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.payment.application.PaymentService;
import ca.ulaval.glo4003.repul.payment.domain.ClientRepository;
import ca.ulaval.glo4003.repul.payment.domain.CreditCardProcessor;
import ca.ulaval.glo4003.repul.payment.domain.client.Client;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientFactory;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

    private final ClientId CLIENT_ID = new ClientId("clientId");
    private final Amount AMOUNT = new Amount(100);
    private final Client clientMock = mock(Client.class);
    private final ClientRepository clientRepositoryMock = mock(ClientRepository.class);
    private final CreditCardProcessor creditCardProcessorMock = mock(
        CreditCardProcessor.class
    );
    private final ClientFactory clientFactoryMock = mock(ClientFactory.class);
    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        paymentService =
            new PaymentService(
                clientRepositoryMock,
                creditCardProcessorMock,
                clientFactoryMock
            );

        when(clientRepositoryMock.findById(CLIENT_ID)).thenReturn(clientMock);
    }

    @Test
    void pay_clientIsCharged() {
        paymentService.pay(CLIENT_ID, AMOUNT);

        verify(clientMock).pay(AMOUNT, creditCardProcessorMock);
    }
}

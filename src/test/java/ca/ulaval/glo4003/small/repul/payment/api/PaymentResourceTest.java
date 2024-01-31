package ca.ulaval.glo4003.small.repul.payment.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.payment.api.PaymentResource;
import ca.ulaval.glo4003.repul.payment.api.dto.request.AddCreditCardRequest;
import ca.ulaval.glo4003.repul.payment.api.dto.request.PaymentRequest;
import ca.ulaval.glo4003.repul.payment.application.PaymentService;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardExpirationDate;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardNumber;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.Ccv;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class PaymentResourceTest {

    private static final String CLIENT_ID = "clientId";
    private static final String CARD_NUMBER = "1234567812345678";
    private static final String CARD_EXPIRATION_DATE = "12-2099";
    private static final String CARD_CCV = "123";
    private static final double AMOUNT = 100.0;
    private final PaymentService paymentServiceMock = mock(PaymentService.class);
    private final AddCreditCardRequest addCreditCardRequest = new AddCreditCardRequest(
        CARD_NUMBER,
        CARD_EXPIRATION_DATE,
        CARD_CCV
    );
    private final PaymentRequest paymentRequest = new PaymentRequest(CLIENT_ID, AMOUNT);
    private final PaymentResource paymentResource = new PaymentResource(
        paymentServiceMock
    );

    @Test
    void createClient_clientIsCreated() {
        Response response = paymentResource.createClient(CLIENT_ID, addCreditCardRequest);

        verify(paymentServiceMock)
            .createClient(
                new ClientId(CLIENT_ID),
                new CardNumber(CARD_NUMBER),
                new CardExpirationDate(CARD_EXPIRATION_DATE),
                new Ccv(CARD_CCV)
            );
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }

    @Test
    void pay_paymentIsMade() {
        Response response = paymentResource.pay(paymentRequest);

        verify(paymentServiceMock).pay(new ClientId(CLIENT_ID), new Amount(AMOUNT));
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}

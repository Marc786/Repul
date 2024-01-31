package ca.ulaval.glo4003.small.repul.finance.infra.payment;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.infra.payment.InternalPaymentClient;
import ca.ulaval.glo4003.repul.payment.api.PaymentResource;
import ca.ulaval.glo4003.repul.payment.api.dto.request.PaymentRequest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InternalPaymentClientTest {

    private final PaymentResource paymentResourceMock = mock(PaymentResource.class);
    private final String CUSTOMER_ID = "customerId";
    private final double AMOUNT = 100.0;
    private final BuyerId buyerIdMock = mock(BuyerId.class);
    private final Amount amountMock = mock(Amount.class);
    private final Bill billMock = mock(Bill.class);
    private final Response responseMock = mock(Response.class);
    private InternalPaymentClient internalPaymentClient;

    @BeforeEach
    void setUp() {
        internalPaymentClient = new InternalPaymentClient(paymentResourceMock);

        when(billMock.getCustomerId()).thenReturn(buyerIdMock);
        when(billMock.getPrice()).thenReturn(amountMock);
        when(buyerIdMock.toString()).thenReturn(CUSTOMER_ID);
        when(amountMock.value()).thenReturn(AMOUNT);
    }

    @Test
    void payBill_paymentResourceReturnsStatusOk_doesNotThrow() {
        when(responseMock.getStatus())
            .thenReturn(Response.Status.NO_CONTENT.getStatusCode());
        PaymentRequest paymentRequest = new PaymentRequest(CUSTOMER_ID, AMOUNT);
        when(paymentResourceMock.pay(paymentRequest)).thenReturn(responseMock);

        assertDoesNotThrow(() -> internalPaymentClient.pay(billMock));
    }
}

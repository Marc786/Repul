package ca.ulaval.glo4003.repul.finance.infra.payment;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.PaymentClient;
import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.payment.api.PaymentResource;
import ca.ulaval.glo4003.repul.payment.api.dto.request.PaymentRequest;

public class InternalPaymentClient implements PaymentClient {

    private final PaymentResource paymentResource;
    private final PaymentRequestAssembler paymentRequestAssembler =
        new PaymentRequestAssembler();

    public InternalPaymentClient(PaymentResource paymentResource) {
        this.paymentResource = paymentResource;
    }

    @Override
    public void pay(Bill bill) {
        BuyerId buyerId = bill.getCustomerId();
        Amount amount = bill.getPrice();
        PaymentRequest paymentRequest = paymentRequestAssembler.toRequest(
            buyerId.toString(),
            amount.value()
        );

        paymentResource.pay(paymentRequest);
    }
}

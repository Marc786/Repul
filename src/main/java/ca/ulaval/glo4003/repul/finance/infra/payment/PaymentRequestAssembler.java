package ca.ulaval.glo4003.repul.finance.infra.payment;

import ca.ulaval.glo4003.repul.payment.api.dto.request.PaymentRequest;

public class PaymentRequestAssembler {

    public PaymentRequest toRequest(String customerId, double amount) {
        return new PaymentRequest(customerId, amount);
    }
}

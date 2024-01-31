package ca.ulaval.glo4003.repul.payment.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.repul.payment.api.PaymentResource;
import ca.ulaval.glo4003.repul.payment.application.PaymentService;
import ca.ulaval.glo4003.repul.payment.domain.ClientRepository;
import ca.ulaval.glo4003.repul.payment.domain.CreditCardProcessor;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientFactory;
import ca.ulaval.glo4003.repul.payment.infra.StripeApi;

public class PaymentResourceFactory {

    private final PaymentService paymentService;

    public PaymentResourceFactory() {
        ClientFactory clientFactory = new ClientFactory();
        CreditCardProcessor creditCardProcessor = new StripeApi();
        ClientRepository clientRepository = ServiceLocator
            .getInstance()
            .getService(ClientRepository.class);
        this.paymentService =
            new PaymentService(clientRepository, creditCardProcessor, clientFactory);
    }

    public PaymentResource create() {
        return new PaymentResource(paymentService);
    }
}

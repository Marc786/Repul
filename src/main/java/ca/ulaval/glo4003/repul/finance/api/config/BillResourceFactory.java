package ca.ulaval.glo4003.repul.finance.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.lib.catalog.MealKitCatalog;
import ca.ulaval.glo4003.lib.catalog.finder.MealKitPriceFinder;
import ca.ulaval.glo4003.repul.finance.api.billing.BillResource;
import ca.ulaval.glo4003.repul.finance.application.bill.BillService;
import ca.ulaval.glo4003.repul.finance.domain.PaymentClient;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.infra.payment.InternalPaymentClient;
import ca.ulaval.glo4003.repul.payment.api.PaymentResource;
import ca.ulaval.glo4003.repul.payment.application.PaymentService;
import ca.ulaval.glo4003.repul.payment.domain.ClientRepository;
import ca.ulaval.glo4003.repul.payment.domain.CreditCardProcessor;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientFactory;
import ca.ulaval.glo4003.repul.payment.infra.StripeApi;

public class BillResourceFactory {

    private final BillService billService;

    public BillResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        BillRepository billRepository = serviceLocator.getService(BillRepository.class);
        MealKitPriceFinder mealKitPriceFinder = serviceLocator.getService(
            MealKitCatalog.class
        );

        PaymentResource paymentResource = createPaymentResource(serviceLocator);
        PaymentClient paymentClient = new InternalPaymentClient(paymentResource);

        this.billService =
            new BillService(billRepository, paymentClient, mealKitPriceFinder);
    }

    public BillResource create() {
        return new BillResource(billService);
    }

    private PaymentResource createPaymentResource(ServiceLocator serviceLocator) {
        ClientRepository clientRepository = serviceLocator.getService(
            ClientRepository.class
        );
        CreditCardProcessor creditCardProcessor = new StripeApi();
        ClientFactory clientFactory = new ClientFactory();
        PaymentService paymentService = new PaymentService(
            clientRepository,
            creditCardProcessor,
            clientFactory
        );

        return new PaymentResource(paymentService);
    }
}

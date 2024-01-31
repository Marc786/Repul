package ca.ulaval.glo4003.context.resource;

import ca.ulaval.glo4003.constant.Constants;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.lib.catalog.MealKitCatalog;
import ca.ulaval.glo4003.lib.semester.registry.SemesterRegistry;
import ca.ulaval.glo4003.middleware.auth.config.AuthFilterFactory;
import ca.ulaval.glo4003.repul.account.api.carrier.CarrierProfileResource;
import ca.ulaval.glo4003.repul.account.api.config.CarrierProfileResourceFactory;
import ca.ulaval.glo4003.repul.account.api.config.CookProfileResourceFactory;
import ca.ulaval.glo4003.repul.account.api.config.CustomerProfileResourceFactory;
import ca.ulaval.glo4003.repul.account.api.cook.CookProfileResource;
import ca.ulaval.glo4003.repul.account.api.customer.CustomerProfileResource;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileRepository;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCarrierProfileRepository;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCookProfileProfileRepository;
import ca.ulaval.glo4003.repul.account.infra.InMemoryCustomerProfileRepository;
import ca.ulaval.glo4003.repul.auth.api.AuthResource;
import ca.ulaval.glo4003.repul.auth.api.config.AuthResourceFactory;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialRepository;
import ca.ulaval.glo4003.repul.auth.infra.credential.InMemoryCredentialRepository;
import ca.ulaval.glo4003.repul.communication.api.CommunicationResource;
import ca.ulaval.glo4003.repul.communication.api.config.CommunicationResourceFactory;
import ca.ulaval.glo4003.repul.finance.api.billing.BillResource;
import ca.ulaval.glo4003.repul.finance.api.config.BillResourceFactory;
import ca.ulaval.glo4003.repul.finance.api.config.ReportResourceFactory;
import ca.ulaval.glo4003.repul.finance.api.report.ReportResource;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.infra.InMemoryBillRepository;
import ca.ulaval.glo4003.repul.kitchen.api.config.CookResourceFactory;
import ca.ulaval.glo4003.repul.kitchen.api.config.MealResourceFactory;
import ca.ulaval.glo4003.repul.kitchen.api.cook.CookResource;
import ca.ulaval.glo4003.repul.kitchen.api.meal.MealResource;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookRepository;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealRepository;
import ca.ulaval.glo4003.repul.kitchen.infra.cook.InMemoryCookRepository;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.InMemoryMealRepository;
import ca.ulaval.glo4003.repul.payment.api.PaymentResource;
import ca.ulaval.glo4003.repul.payment.api.config.PaymentResourceFactory;
import ca.ulaval.glo4003.repul.payment.domain.ClientRepository;
import ca.ulaval.glo4003.repul.payment.infra.InMemoryClientRepository;
import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.api.config.ShipmentResourceFactory;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemRepository;
import ca.ulaval.glo4003.repul.shipment.infra.InMemoryShipmentItemRepository;
import ca.ulaval.glo4003.repul.subscription.api.SubscriberResource;
import ca.ulaval.glo4003.repul.subscription.api.config.SubscriptionResourceFactory;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberRepository;
import ca.ulaval.glo4003.repul.subscription.infra.InMemorySubscriberRepository;
import jakarta.ws.rs.container.ContainerRequestFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

public class ResourceConfigFactory implements ResourceFactory {

    private static final String ROOT_PACKAGE = "ca.ulaval.glo4003.repul";
    private static final String HTTP_PACKAGE = "ca.ulaval.glo4003.http";

    @Override
    public ResourceConfig create() {
        setupRepositories();

        ContainerRequestFilter containerRequestFilter = new AuthFilterFactory().create();
        BillResource billResource = new BillResourceFactory().create();
        PaymentResource paymentResource = new PaymentResourceFactory().create();
        ReportResource reportResource = new ReportResourceFactory().create();
        SubscriberResource subscriberResource = new SubscriptionResourceFactory()
            .create();
        ShipmentResource shipmentResource = new ShipmentResourceFactory().create();
        CookProfileResource cookProfileResource = new CookProfileResourceFactory()
            .create();
        CarrierProfileResource carrierProfileResource =
            new CarrierProfileResourceFactory().create();
        CustomerProfileResource customerProfileResource =
            new CustomerProfileResourceFactory().create();
        MealResource mealResource = new MealResourceFactory().create();
        CookResource cookResource = new CookResourceFactory().create();
        CommunicationResource communicationResource = new CommunicationResourceFactory()
            .create();
        AuthResource authResource = new AuthResourceFactory().create();

        return new ResourceConfig()
            .packages(ROOT_PACKAGE, HTTP_PACKAGE)
            .property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true)
            .register(containerRequestFilter)
            .register(paymentResource)
            .register(billResource)
            .register(reportResource)
            .register(subscriberResource)
            .register(cookProfileResource)
            .register(carrierProfileResource)
            .register(customerProfileResource)
            .register(mealResource)
            .register(cookResource)
            .register(communicationResource)
            .register(shipmentResource)
            .register(authResource);
    }

    private void setupRepositories() {
        CustomerProfileRepository customerProfileRepository =
            new InMemoryCustomerProfileRepository();
        CookProfileRepository cookProfileRepository =
            new InMemoryCookProfileProfileRepository();
        CredentialRepository credentialRepository = new InMemoryCredentialRepository();
        SubscriberRepository subscriberRepository = new InMemorySubscriberRepository();
        CarrierProfileRepository carrierProfileRepository =
            new InMemoryCarrierProfileRepository();
        BillRepository billRepository = new InMemoryBillRepository();
        ClientRepository clientRepository = new InMemoryClientRepository();
        ShipmentItemRepository shipmentItemRepository =
            new InMemoryShipmentItemRepository();
        MealRepository mealRepository = new InMemoryMealRepository();
        CookRepository cookRepository = new InMemoryCookRepository();
        SemesterRegistry semesterRegistry = new SemesterRegistry(
            Constants.Path.JSON_SEMESTERS
        );
        MealKitCatalog mealKitCatalog = new MealKitCatalog();

        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        serviceLocator.registerService(
            CustomerProfileRepository.class,
            customerProfileRepository
        );
        serviceLocator.registerService(
            CookProfileRepository.class,
            cookProfileRepository
        );
        serviceLocator.registerService(CredentialRepository.class, credentialRepository);
        serviceLocator.registerService(SubscriberRepository.class, subscriberRepository);
        serviceLocator.registerService(
            CarrierProfileRepository.class,
            carrierProfileRepository
        );
        serviceLocator.registerService(BillRepository.class, billRepository);
        serviceLocator.registerService(ClientRepository.class, clientRepository);
        serviceLocator.registerService(
            ShipmentItemRepository.class,
            shipmentItemRepository
        );
        serviceLocator.registerService(SemesterRegistry.class, semesterRegistry);
        serviceLocator.registerService(MealKitCatalog.class, mealKitCatalog);
        serviceLocator.registerService(MealRepository.class, mealRepository);
        serviceLocator.registerService(CookRepository.class, cookRepository);
    }
}

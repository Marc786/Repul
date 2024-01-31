package ca.ulaval.glo4003.repul.subscription.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.lib.semester.registry.SemesterRegistry;
import ca.ulaval.glo4003.lib.semester.registry.finder.SubscriptionSemesterFinder;
import ca.ulaval.glo4003.repul.finance.api.billing.BillResource;
import ca.ulaval.glo4003.repul.finance.api.config.BillResourceFactory;
import ca.ulaval.glo4003.repul.kitchen.api.config.MealResourceFactory;
import ca.ulaval.glo4003.repul.kitchen.api.meal.MealResource;
import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.api.config.ShipmentResourceFactory;
import ca.ulaval.glo4003.repul.subscription.api.SubscriberResource;
import ca.ulaval.glo4003.repul.subscription.application.SubscriberService;
import ca.ulaval.glo4003.repul.subscription.domain.BillClient;
import ca.ulaval.glo4003.repul.subscription.domain.MealClient;
import ca.ulaval.glo4003.repul.subscription.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberRepository;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.SubscriptionFactory;
import ca.ulaval.glo4003.repul.subscription.infra.bill.InternalBillClient;
import ca.ulaval.glo4003.repul.subscription.infra.bill.MealKitBillRequestAssembler;
import ca.ulaval.glo4003.repul.subscription.infra.cook.InternalMealClient;
import ca.ulaval.glo4003.repul.subscription.infra.shipment.InternalShipmentClient;

public class SubscriptionResourceFactory {

    private final SubscriberService subscriberService;

    public SubscriptionResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        SubscriberRepository subscriberRepository = serviceLocator.getService(
            SubscriberRepository.class
        );
        SubscriptionSemesterFinder subscriptionSemesterFinder = serviceLocator.getService(
            SemesterRegistry.class
        );
        SubscriptionFactory subscriptionFactory = new SubscriptionFactory();

        BillResource billResource = new BillResourceFactory().create();
        new MealKitBillRequestAssembler();
        BillClient billClient = new InternalBillClient(billResource);

        MealResource mealResource = new MealResourceFactory().create();
        MealClient mealClient = new InternalMealClient(mealResource);

        ShipmentResource shipmentResource = new ShipmentResourceFactory().create();
        ShipmentClient shipmentClient = new InternalShipmentClient(shipmentResource);

        this.subscriberService =
            new SubscriberService(
                subscriptionFactory,
                subscriberRepository,
                subscriptionSemesterFinder,
                billClient,
                mealClient,
                shipmentClient
            );
    }

    public SubscriberResource create() {
        return new SubscriberResource(subscriberService);
    }
}

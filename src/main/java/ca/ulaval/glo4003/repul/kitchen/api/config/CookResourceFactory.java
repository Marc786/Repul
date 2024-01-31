package ca.ulaval.glo4003.repul.kitchen.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.repul.kitchen.api.cook.CookResource;
import ca.ulaval.glo4003.repul.kitchen.application.CookService;
import ca.ulaval.glo4003.repul.kitchen.domain.ShipmentClient;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookRepository;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.MealFinder;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealRepository;
import ca.ulaval.glo4003.repul.kitchen.infra.shipment.InternalShipmentClient;
import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.api.config.ShipmentResourceFactory;

public class CookResourceFactory {

    private final CookService cookService;

    public CookResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        CookRepository cookRepository = serviceLocator.getService(CookRepository.class);
        MealFinder mealFinder = serviceLocator.getService(MealRepository.class);
        ShipmentResource shipmentResource = new ShipmentResourceFactory().create();
        ShipmentClient shipmentClient = new InternalShipmentClient(shipmentResource);
        this.cookService = new CookService(cookRepository, mealFinder, shipmentClient);
    }

    public CookResource create() {
        return new CookResource(cookService);
    }
}

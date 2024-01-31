package ca.ulaval.glo4003.repul.kitchen.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.lib.catalog.MealKitCatalog;
import ca.ulaval.glo4003.lib.catalog.finder.MealRecipeFinder;
import ca.ulaval.glo4003.repul.kitchen.api.meal.MealResource;
import ca.ulaval.glo4003.repul.kitchen.application.MealService;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealFactory;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealRepository;

public class MealResourceFactory {

    private final MealService mealService;

    public MealResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        MealRepository mealRepository = serviceLocator.getService(MealRepository.class);
        MealRecipeFinder mealRecipeFinder = serviceLocator.getService(
            MealKitCatalog.class
        );
        MealFactory mealFactory = new MealFactory(mealRecipeFinder);
        this.mealService = new MealService(mealFactory, mealRepository);
    }

    public MealResource create() {
        return new MealResource(mealService);
    }
}

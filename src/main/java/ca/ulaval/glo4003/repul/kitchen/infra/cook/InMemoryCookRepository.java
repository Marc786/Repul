package ca.ulaval.glo4003.repul.kitchen.infra.cook;

import ca.ulaval.glo4003.repul.kitchen.domain.cook.Cook;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookRepository;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.infra.cook.exception.CookNotFoundException;
import ca.ulaval.glo4003.repul.kitchen.infra.meal.cloner.MealFastCloner;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCookRepository implements CookRepository {

    private final Cloner cloner = new Cloner();
    private final List<Cook> cooks = new ArrayList<>();

    public InMemoryCookRepository() {
        cloner.registerFastCloner(Meal.class, new MealFastCloner());
    }

    @Override
    public void save(Cook cook) {
        removeIfExisting(cook);
        cooks.add(cook);
    }

    @Override
    public Cook findById(CookId cookId) {
        Cook cookFound = cooks
            .stream()
            .filter(cook -> cook.getCookId().equals(cookId))
            .findFirst()
            .orElseThrow(() -> new CookNotFoundException(cookId));
        return cloner.deepClone(cookFound);
    }

    private void removeIfExisting(Cook cook) {
        cooks.removeIf(existingCook -> existingCook.getCookId().equals(cook.getCookId()));
    }
}

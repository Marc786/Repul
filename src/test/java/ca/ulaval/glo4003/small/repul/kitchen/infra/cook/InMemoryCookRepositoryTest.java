package ca.ulaval.glo4003.small.repul.kitchen.infra.cook;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ca.ulaval.glo4003.repul.kitchen.domain.cook.Cook;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import ca.ulaval.glo4003.repul.kitchen.infra.cook.InMemoryCookRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryCookRepositoryTest {

    private static final String COOK_ID_STRING = UUID.randomUUID().toString();
    private static final CookId COOK_ID = new CookId(COOK_ID_STRING);
    private static final Cook COOK = new Cook(COOK_ID);

    private InMemoryCookRepository inMemoryCookRepository;

    @BeforeEach
    void setup() {
        inMemoryCookRepository = new InMemoryCookRepository();
    }

    @Test
    void save_cookIsSaved() {
        inMemoryCookRepository.save(COOK);

        Cook actualCook = inMemoryCookRepository.findById(COOK_ID);
        assertThat(actualCook).usingRecursiveComparison().isEqualTo(COOK);
    }

    @Test
    void findById_cookIsReturned() {
        inMemoryCookRepository.save(COOK);

        Cook actualCook = inMemoryCookRepository.findById(COOK_ID);
        assertThat(actualCook).usingRecursiveComparison().isEqualTo(COOK);
    }
}

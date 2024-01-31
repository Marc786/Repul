package ca.ulaval.glo4003.repul.kitchen.domain.cook;

public interface CookRepository {
    void save(Cook cook);

    Cook findById(CookId cookId);
}

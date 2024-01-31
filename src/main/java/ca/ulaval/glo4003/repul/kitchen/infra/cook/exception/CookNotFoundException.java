package ca.ulaval.glo4003.repul.kitchen.infra.cook.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;

public class CookNotFoundException extends ItemNotFoundException {

    public CookNotFoundException(CookId cookId) {
        super(String.format("Cook with id %s was not found", cookId));
    }
}

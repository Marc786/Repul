package ca.ulaval.glo4003.repul.shipment.domain.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;

public class ShipmentItemNotFoundException extends ItemNotFoundException {

    private static final String ERROR_MESSAGE = "Shipment item with id %s does not exist";

    public ShipmentItemNotFoundException(ShipmentItemId id) {
        super(String.format(ERROR_MESSAGE, id));
    }
}

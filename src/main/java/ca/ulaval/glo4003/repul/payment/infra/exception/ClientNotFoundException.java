package ca.ulaval.glo4003.repul.payment.infra.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;

public class ClientNotFoundException extends ItemNotFoundException {

    private static final String ERROR_MESSAGE = "Client with id %s not found";

    public ClientNotFoundException(ClientId clientId) {
        super(String.format(ERROR_MESSAGE, clientId.toString()));
    }
}

package ca.ulaval.glo4003.repul.payment.domain;

import ca.ulaval.glo4003.repul.payment.domain.client.Client;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;

public interface ClientRepository {
    Client findById(ClientId clientId);

    void save(Client client);
}

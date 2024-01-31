package ca.ulaval.glo4003.repul.payment.infra;

import ca.ulaval.glo4003.repul.payment.domain.ClientRepository;
import ca.ulaval.glo4003.repul.payment.domain.client.Client;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;
import ca.ulaval.glo4003.repul.payment.infra.exception.ClientNotFoundException;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class InMemoryClientRepository implements ClientRepository {

    private final Cloner cloner = new Cloner();
    private final List<Client> clients = new ArrayList<>();

    @Override
    public void save(Client client) {
        removeIfExisting(client);
        clients.add(client);
    }

    @Override
    public Client findById(ClientId clientId) {
        Client clientFound = clients
            .stream()
            .filter(client -> client.getId().equals(clientId))
            .findFirst()
            .orElseThrow(() -> new ClientNotFoundException(clientId));
        return cloner.deepClone(clientFound);
    }

    private void removeIfExisting(Client client) {
        clients.removeIf(existingClient -> existingClient.equals(client));
    }
}

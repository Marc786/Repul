package ca.ulaval.glo4003.small.repul.payment.infra;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.repul.payment.domain.ClientRepository;
import ca.ulaval.glo4003.repul.payment.domain.client.Client;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardExpirationDate;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardNumber;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.Ccv;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CreditCard;
import ca.ulaval.glo4003.repul.payment.infra.InMemoryClientRepository;
import ca.ulaval.glo4003.repul.payment.infra.exception.ClientNotFoundException;
import org.junit.jupiter.api.Test;

class InMemoryClientRepositoryTest {

    private final ClientId CLIENT_ID = new ClientId("idul123");
    private final CardNumber CARD_NUMBER = new CardNumber("1234567812345678");
    private final CardExpirationDate CARD_EXPIRATION_DATE = new CardExpirationDate(
        "12-2099"
    );
    private final Ccv CCV = new Ccv("123");
    private final Client CLIENT = new Client(
        CLIENT_ID,
        new CreditCard(CARD_NUMBER, CARD_EXPIRATION_DATE, CCV)
    );
    private final ClientRepository clientRepository = new InMemoryClientRepository();

    @Test
    void saveClient_clientIsSaved() {
        clientRepository.save(CLIENT);

        Client clientFound = clientRepository.findById(CLIENT_ID);
        assertNotSame(CLIENT, clientFound);
        assertEquals(CLIENT, clientFound);
    }

    @Test
    void existingClient_findById_clientIsReturned() {
        clientRepository.save(CLIENT);

        Client clientFound = clientRepository.findById(CLIENT_ID);

        assertNotSame(CLIENT, clientFound);
        assertEquals(CLIENT, clientFound);
    }

    @Test
    void newClient_findById_clientNotFoundExceptionIsThrown() {
        assertThrows(
            ClientNotFoundException.class,
            () -> clientRepository.findById(CLIENT_ID)
        );
    }
}

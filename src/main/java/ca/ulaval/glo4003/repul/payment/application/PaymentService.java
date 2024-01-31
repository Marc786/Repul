package ca.ulaval.glo4003.repul.payment.application;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.payment.domain.ClientRepository;
import ca.ulaval.glo4003.repul.payment.domain.CreditCardProcessor;
import ca.ulaval.glo4003.repul.payment.domain.client.Client;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientFactory;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardExpirationDate;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardNumber;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.Ccv;
import ca.ulaval.glo4003.repul.payment.domain.exception.CreditCardNotFoundException;

public class PaymentService {

    private final ClientRepository clientRepository;
    private final CreditCardProcessor creditCardProcessor;
    private final ClientFactory clientFactory;

    public PaymentService(
        ClientRepository clientRepository,
        CreditCardProcessor creditCardProcessor,
        ClientFactory clientFactory
    ) {
        this.clientRepository = clientRepository;
        this.creditCardProcessor = creditCardProcessor;
        this.clientFactory = clientFactory;
    }

    public void pay(ClientId clientId, Amount amount) {
        Client client;
        try {
            client = clientRepository.findById(clientId);
        } catch (Exception e) {
            throw new CreditCardNotFoundException();
        }

        client.pay(amount, creditCardProcessor);

        clientRepository.save(client);
    }

    public void createClient(
        ClientId clientId,
        CardNumber creditCardNumber,
        CardExpirationDate creditCardExpirationDate,
        Ccv creditCardCcv
    ) {
        Client client = clientFactory.create(
            clientId,
            creditCardNumber,
            creditCardExpirationDate,
            creditCardCcv
        );

        clientRepository.save(client);
    }
}

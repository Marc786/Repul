package ca.ulaval.glo4003.repul.payment.domain.client;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.payment.domain.CreditCardProcessor;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CreditCard;

public class Client {

    private final ClientId clientId;
    private final CreditCard creditCard;

    public Client(ClientId clientId, CreditCard creditCard) {
        this.clientId = clientId;
        this.creditCard = creditCard;
    }

    public ClientId getId() {
        return clientId;
    }

    public void pay(Amount amount, CreditCardProcessor creditCardProcessor) {
        creditCard.validate();
        creditCardProcessor.processPayment(creditCard, amount);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Client client = (Client) obj;
        return clientId.equals(client.clientId) && creditCard.equals(client.creditCard);
    }

    @Override
    public int hashCode() {
        return clientId.hashCode() + creditCard.hashCode();
    }
}

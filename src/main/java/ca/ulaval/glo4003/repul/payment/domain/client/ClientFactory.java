package ca.ulaval.glo4003.repul.payment.domain.client;

import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardExpirationDate;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardNumber;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.Ccv;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CreditCard;

public class ClientFactory {

    public Client create(
        ClientId clientId,
        CardNumber creditCardNumber,
        CardExpirationDate creditCardExpirationDate,
        Ccv creditCardCcv
    ) {
        CreditCard creditCard = new CreditCard(
            creditCardNumber,
            creditCardExpirationDate,
            creditCardCcv
        );
        creditCard.validate();
        return new Client(clientId, creditCard);
    }
}

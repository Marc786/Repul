package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.repul.payment.api.dto.request.AddCreditCardRequest;

public class AddCreditCardRequestFixture {

    private final String creditCardNumber = "1234567890123456";
    private final String creditCardExpirationDate = "01-2030";
    private final String creditCardCvv = "123";

    public AddCreditCardRequest build() {
        return new AddCreditCardRequest(
            creditCardNumber,
            creditCardExpirationDate,
            creditCardCvv
        );
    }
}

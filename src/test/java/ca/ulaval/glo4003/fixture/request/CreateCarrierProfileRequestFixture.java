package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.repul.account.api.carrier.dto.request.CreateCarrierProfileRequest;

public class CreateCarrierProfileRequestFixture {

    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String email = "speak.white.007@gmail.com";
    private final String password = "password";

    public CreateCarrierProfileRequest build() {
        return new CreateCarrierProfileRequest(firstName, lastName, email, password);
    }
}

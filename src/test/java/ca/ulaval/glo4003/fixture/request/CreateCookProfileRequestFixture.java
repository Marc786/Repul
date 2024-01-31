package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.repul.account.api.cook.dto.request.CreateCookProfileRequest;

public class CreateCookProfileRequestFixture {

    private final String email = "raphcook1234@ulaval.ca";
    private final String password = "1";
    private final String firstName = "raph";
    private final String lastName = "cook";

    public CreateCookProfileRequest build() {
        return new CreateCookProfileRequest(firstName, lastName, email, password);
    }
}

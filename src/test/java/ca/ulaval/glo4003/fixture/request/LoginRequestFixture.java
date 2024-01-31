package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.repul.auth.api.dto.request.LoginRequest;

public class LoginRequestFixture {

    private String email = "raph1234@ulaval.ca";
    private final String password = "123";
    private String role = "CUSTOMER";

    public LoginRequest build() {
        return new LoginRequest(email, password, role);
    }

    public LoginRequestFixture withEmail(String email) {
        this.email = email;
        return this;
    }

    public LoginRequestFixture withRole(String role) {
        this.role = role;
        return this;
    }
}

package ca.ulaval.glo4003.small.repul.auth.api;

import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.fixture.auth.CredentialFixture;
import ca.ulaval.glo4003.fixture.request.LoginRequestFixture;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.auth.api.AuthResource;
import ca.ulaval.glo4003.repul.auth.api.dto.request.LoginRequest;
import ca.ulaval.glo4003.repul.auth.api.dto.response.TokenResponse;
import ca.ulaval.glo4003.repul.auth.application.AuthService;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthResourceTest {

    private static final String TOKEN = "token";
    private static final LoginRequestFixture loginRequestFixture =
        new LoginRequestFixture();
    private static final CredentialFixture credentialFixture = new CredentialFixture();
    private final LoginRequest loginRequest = loginRequestFixture.build();
    private final Credential customerCredential =
        credentialFixture.buildCustomerCredential();
    private final TokenAuth tokenAuth = mock(TokenAuth.class);
    private final AuthService authService = mock(AuthService.class);
    private AuthResource authResource;

    @BeforeEach
    void setUp() {
        authResource = new AuthResource(authService, tokenAuth);
    }

    @Test
    void login_returnsOkStatusCode() {
        EmailAddress email = new EmailAddress(loginRequest.email());
        Password password = new Password(loginRequest.password());
        Role role = Role.fromString(loginRequest.role());
        when(authService.login(email, password, role)).thenReturn(customerCredential);
        when(
            tokenAuth.generateToken(
                customerCredential.getId(),
                EXPIRATION_DELAY_IN_MS,
                customerCredential.getRole()
            )
        )
            .thenReturn(TOKEN);

        int actualStatusCode = authResource.login(loginRequest).getStatus();

        assertEquals(Response.Status.OK.getStatusCode(), actualStatusCode);
    }

    @Test
    void login_returnsGeneratedTokenResponse() {
        EmailAddress email = new EmailAddress(loginRequest.email());
        Password password = new Password(loginRequest.password());
        Role role = Role.fromString(loginRequest.role());
        when(authService.login(email, password, role)).thenReturn(customerCredential);
        when(
            tokenAuth.generateToken(
                customerCredential.getId(),
                EXPIRATION_DELAY_IN_MS,
                customerCredential.getRole()
            )
        )
            .thenReturn(TOKEN);

        Response actualResponse = authResource.login(loginRequest);

        Response expectedResponse = Response.ok(new TokenResponse(TOKEN)).build();
        assertEquals(expectedResponse.getEntity(), actualResponse.getEntity());
    }
}

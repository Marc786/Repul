package ca.ulaval.glo4003.repul.auth.api;

import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.auth.api.dto.request.LoginRequest;
import ca.ulaval.glo4003.repul.auth.api.dto.response.TokenResponse;
import ca.ulaval.glo4003.repul.auth.application.AuthService;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final AuthService authService;
    private final TokenAuth tokenAuth;

    public AuthResource(AuthService authService, TokenAuth tokenAuth) {
        this.authService = authService;
        this.tokenAuth = tokenAuth;
    }

    @POST
    @Path("/login")
    public Response login(@NotNull @Valid LoginRequest loginRequest) {
        EmailAddress email = new EmailAddress(loginRequest.email());
        Password password = new Password(loginRequest.password());
        Role role = Role.fromString(loginRequest.role());

        Credential credential = authService.login(email, password, role);
        String token = tokenAuth.generateToken(
            credential.getId(),
            EXPIRATION_DELAY_IN_MS,
            credential.getRole()
        );

        return Response.ok().entity(new TokenResponse(token)).build();
    }
}

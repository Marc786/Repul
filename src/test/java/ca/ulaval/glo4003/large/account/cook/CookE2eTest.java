package ca.ulaval.glo4003.large.account.cook;

import static ca.ulaval.glo4003.RepulServer.BASE_URI;
import static ca.ulaval.glo4003.constant.Constants.Auth.AUTHORIZATION_HEADER_NAME;
import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;
import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.context.resource.ResourceConfigFactory;
import ca.ulaval.glo4003.fixture.request.CreateCookProfileRequestFixture;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.api.cook.dto.request.CreateCookProfileRequest;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import ca.ulaval.glo4003.repul.auth.infra.credential.InMemoryCredentialRepository;
import ca.ulaval.glo4003.repul.auth.infra.token.JWTAuth;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookE2eTest {

    private static final EmailAddress ADMIN_EMAIL = new EmailAddress("admin@email.com");
    private static final String CONTENT_TYPE = "application/json";
    private static final String COOKS_PATH = "/cooks";
    private static final String AUTHORIZATION_PREFIX = "Bearer %s";

    private final CreateCookProfileRequestFixture createCookProfileRequestFixture =
        new CreateCookProfileRequestFixture();
    private final TokenAuth tokenAuth = new JWTAuth();

    private static CredentialId adminCredentialId;
    private Server server;

    @BeforeAll
    static void setupAll() {
        InMemoryCredentialRepository inMemoryCredentialRepository =
            new InMemoryCredentialRepository();
        inMemoryCredentialRepository.save(
            new Credential(
                new CredentialId(UUID.randomUUID().toString()),
                ADMIN_EMAIL,
                new Password("password"),
                Role.ADMIN
            )
        );

        adminCredentialId =
            inMemoryCredentialRepository
                .findUserCredential(ADMIN_EMAIL, Role.ADMIN)
                .getId();
    }

    @BeforeEach
    void setup() {
        ResourceConfig config = new ResourceConfigFactory().create();

        server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
    }

    @AfterEach
    void tearDown() throws Exception {
        server.stop();
    }

    @Test
    void createCook_returnsCreated() {
        CreateCookProfileRequest createCookProfileRequest =
            createCookProfileRequestFixture.build();

        createCookAndCheckStatusCreated(createCookProfileRequest);
    }

    private void createCookAndCheckStatusCreated(
        CreateCookProfileRequest createCookProfileRequest
    ) {
        given()
            .contentType(CONTENT_TYPE)
            .body(createCookProfileRequest)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        adminCredentialId,
                        EXPIRATION_DELAY_IN_MS,
                        Role.ADMIN
                    )
                )
            )
            .when()
            .post(COOKS_PATH)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }
}

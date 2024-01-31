package ca.ulaval.glo4003.large.auth;

import static ca.ulaval.glo4003.RepulServer.BASE_URI;
import static ca.ulaval.glo4003.constant.Constants.Auth.AUTHORIZATION_HEADER_NAME;
import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import ca.ulaval.glo4003.context.resource.ResourceConfigFactory;
import ca.ulaval.glo4003.fixture.request.AddCreditCardRequestFixture;
import ca.ulaval.glo4003.fixture.request.CreateCustomerProfileRequestFixture;
import ca.ulaval.glo4003.fixture.request.LoginRequestFixture;
import ca.ulaval.glo4003.repul.account.api.customer.dto.request.CreateCustomerProfileRequest;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.auth.api.dto.request.LoginRequest;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import ca.ulaval.glo4003.repul.auth.infra.token.JWTAuth;
import ca.ulaval.glo4003.repul.payment.api.dto.request.AddCreditCardRequest;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthFilterE2eTest {

    private static final String CUSTOMER_PROFILE_ID_STRING = "ralol03";
    private static final CustomerProfileId CUSTOMER_PROFILE_ID = new CustomerProfileId(
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final CredentialId CUSTOMER_PROFILE_CREDENTIAL_ID = new CredentialId(
        CUSTOMER_PROFILE_ID
    );
    private static final String EMAIL = "raph03@ulaval.ca";
    private static final String OTHER_CUSTOMER_PROFILE_ID_STRING = "ralol04";
    private static final String OTHER_EMAIL = "raph12@ulaval.ca";
    private static final String OTHER_STUDENT_CARD_NUMBER = "987654321";
    private static final String CONTENT_TYPE = "application/json";
    private static final String CUSTOMERS_PATH = "/customers";
    private static final String GET_CUSTOMERS_PATH = String.format(
        "%s/%s",
        CUSTOMERS_PATH,
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String GET_OTHER_CUSTOMER_PATH = String.format(
        "%s/%s",
        CUSTOMERS_PATH,
        OTHER_CUSTOMER_PROFILE_ID_STRING
    );
    private static final String LOGIN_PATH = "/login";
    private static final String AUTHORIZATION_PREFIX = "Bearer %s";
    private static final String INVALID_TOKEN = "invalidToken";
    private static final String MEAL_KITS_TO_PREPARE_PATH = "/meals?endDate=2024-09-15";
    private static final String CLIENTS_PATH = "/clients";
    private static final String CREDIT_CARD_PATH = "/credit-card";
    private static final String CLIENT_CREDIT_CARD_PATH = String.format(
        "%s/%s%s",
        CLIENTS_PATH,
        CUSTOMER_PROFILE_ID_STRING,
        CREDIT_CARD_PATH
    );
    private static final String OTHER_CLIENT_CREDIT_CARD_PATH = String.format(
        "%s/%s%s",
        CLIENTS_PATH,
        OTHER_CUSTOMER_PROFILE_ID_STRING,
        CREDIT_CARD_PATH
    );

    private final CreateCustomerProfileRequestFixture createCustomerProfileRequestFixture =
        new CreateCustomerProfileRequestFixture();
    private final AddCreditCardRequestFixture addCreditCardRequestFixture =
        new AddCreditCardRequestFixture();
    private final LoginRequestFixture loginRequestFixture = new LoginRequestFixture();

    private final TokenAuth tokenAuth = new JWTAuth();

    private Server server;

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
    void createCustomerAndLogIn_getCustomer_returnsOK() {
        createCustomer();
        logIn();

        given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        CUSTOMER_PROFILE_CREDENTIAL_ID,
                        EXPIRATION_DELAY_IN_MS,
                        Role.CUSTOMER
                    )
                )
            )
            .when()
            .get(GET_CUSTOMERS_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void noTokenInRequest_whenGetCustomer_returnsUnauthorized() {
        given()
            .contentType(CONTENT_TYPE)
            .when()
            .get(GET_CUSTOMERS_PATH)
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void invalidTokenInRequest_whenGetCustomer_returnsUnauthorized() {
        given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(AUTHORIZATION_PREFIX, INVALID_TOKEN)
            )
            .when()
            .get(GET_CUSTOMERS_PATH)
            .then()
            .statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    void customerCreatesAccountAndLogin_whenGetMealKitsToPrepare_returnsForbidden() {
        createCustomer();
        logIn();

        given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        CUSTOMER_PROFILE_CREDENTIAL_ID,
                        EXPIRATION_DELAY_IN_MS,
                        Role.CUSTOMER
                    )
                )
            )
            .when()
            .get(MEAL_KITS_TO_PREPARE_PATH)
            .then()
            .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    void customerCreatesAccountAndLogin_whenGetOtherExistingCustomer_returnsForbidden() {
        createCustomer();
        createOtherCustomer();
        logIn();

        given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        CUSTOMER_PROFILE_CREDENTIAL_ID,
                        EXPIRATION_DELAY_IN_MS,
                        Role.CUSTOMER
                    )
                )
            )
            .when()
            .get(GET_OTHER_CUSTOMER_PATH)
            .then()
            .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    void customerCreatesAccountAndLogin_whenGetOtherNonExistingCustomer_returnsForbidden() {
        createCustomer();
        logIn();

        given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        CUSTOMER_PROFILE_CREDENTIAL_ID,
                        EXPIRATION_DELAY_IN_MS,
                        Role.CUSTOMER
                    )
                )
            )
            .when()
            .get(GET_OTHER_CUSTOMER_PATH)
            .then()
            .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    void createAccountAndLogin_whenSetCreditCard_returnsOk() {
        createCustomer();
        logIn();
        AddCreditCardRequest addCreditCardRequest = addCreditCardRequestFixture.build();

        given()
            .contentType(CONTENT_TYPE)
            .body(addCreditCardRequest)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        CUSTOMER_PROFILE_CREDENTIAL_ID,
                        EXPIRATION_DELAY_IN_MS,
                        Role.CUSTOMER
                    )
                )
            )
            .when()
            .post(CLIENT_CREDIT_CARD_PATH)
            .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    void createAccountAndLogin_whenSetCreditCardToOtherCustomer_returnsForbidden() {
        createCustomer();
        logIn();
        AddCreditCardRequest addCreditCardRequest = addCreditCardRequestFixture.build();

        given()
            .contentType(CONTENT_TYPE)
            .body(addCreditCardRequest)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        CUSTOMER_PROFILE_CREDENTIAL_ID,
                        EXPIRATION_DELAY_IN_MS,
                        Role.CUSTOMER
                    )
                )
            )
            .when()
            .post(OTHER_CLIENT_CREDIT_CARD_PATH)
            .then()
            .statusCode(Response.Status.FORBIDDEN.getStatusCode());
    }

    private void createCustomer() {
        CreateCustomerProfileRequest createCustomerProfileRequest =
            createCustomerProfileRequestFixture
                .withCustomerProfileId(CUSTOMER_PROFILE_ID_STRING)
                .withEmail(EMAIL)
                .build();

        given()
            .contentType(CONTENT_TYPE)
            .body(createCustomerProfileRequest)
            .when()
            .post(CUSTOMERS_PATH)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private void createOtherCustomer() {
        CreateCustomerProfileRequest createCustomerProfileRequest =
            createCustomerProfileRequestFixture
                .withCustomerProfileId(OTHER_CUSTOMER_PROFILE_ID_STRING)
                .withEmail(OTHER_EMAIL)
                .withStudentCardNumber(OTHER_STUDENT_CARD_NUMBER)
                .build();

        given()
            .contentType(CONTENT_TYPE)
            .body(createCustomerProfileRequest)
            .when()
            .post(CUSTOMERS_PATH)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private void logIn() {
        LoginRequest loginRequest = loginRequestFixture
            .withEmail(EMAIL)
            .withRole(Role.CUSTOMER.toString())
            .build();

        given()
            .contentType(CONTENT_TYPE)
            .body(loginRequest)
            .when()
            .post(LOGIN_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("token", notNullValue());
    }
}

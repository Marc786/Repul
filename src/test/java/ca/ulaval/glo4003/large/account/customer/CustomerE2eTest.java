package ca.ulaval.glo4003.large.account.customer;

import static ca.ulaval.glo4003.RepulServer.BASE_URI;
import static ca.ulaval.glo4003.constant.Constants.Auth.AUTHORIZATION_HEADER_NAME;
import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.context.resource.ResourceConfigFactory;
import ca.ulaval.glo4003.fixture.request.AddCreditCardRequestFixture;
import ca.ulaval.glo4003.fixture.request.CreateCustomerProfileRequestFixture;
import ca.ulaval.glo4003.repul.account.api.customer.dto.request.CreateCustomerProfileRequest;
import ca.ulaval.glo4003.repul.account.api.customer.dto.response.CustomerProfileResponse;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
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

class CustomerE2eTest {

    private static final String CUSTOMER_ID_STRING = "ralol03";
    private static final CustomerProfileId CUSTOMER_ID = new CustomerProfileId(
        CUSTOMER_ID_STRING
    );
    private static final CredentialId CUSTOMER_CREDENTIAL_ID = new CredentialId(
        CUSTOMER_ID
    );
    private static final String CONTENT_TYPE = "application/json";
    private static final String GET_CUSTOMER_PATH = "/customers/ralol03";
    private static final String CUSTOMERS_PATH = "/customers";
    private static final String AUTHORIZATION_PREFIX = "Bearer %s";
    private static final String CREDIT_CARD_PATH = "/clients/ralol03/credit-card";

    private final CreateCustomerProfileRequestFixture createCustomerProfileRequestFixture =
        new CreateCustomerProfileRequestFixture();
    private final AddCreditCardRequestFixture addCreditCardRequestFixture =
        new AddCreditCardRequestFixture();

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
    void createCustomer_returnsCreated() {
        CreateCustomerProfileRequest createCustomerProfileRequest =
            createCustomerProfileRequestFixture
                .withCustomerProfileId(CUSTOMER_ID_STRING)
                .build();
        createCustomerProfileRequestFixture
            .withCustomerProfileId(CUSTOMER_ID_STRING)
            .build();

        createCustomerAndCheckStatusCreated(createCustomerProfileRequest);
    }

    @Test
    void createCustomer_putCreditCard_returnsNoContent() {
        createCustomer();
        AddCreditCardRequest addCreditCardRequest = addCreditCardRequestFixture.build();

        addCreditCardAndCheckStatusNoContent(addCreditCardRequest);
    }

    @Test
    void createCustomer_getCustomer_returnsCustomerDetails() {
        createCustomer();
        CreateCustomerProfileRequest createCustomerRequest =
            new CreateCustomerProfileRequestFixture().build();

        CustomerProfileResponse customerProfileResponse = given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        CUSTOMER_CREDENTIAL_ID,
                        EXPIRATION_DELAY_IN_MS,
                        Role.CUSTOMER
                    )
                )
            )
            .when()
            .get(GET_CUSTOMER_PATH)
            .as(CustomerProfileResponse.class);

        assertEquals(
            createCustomerRequest.firstName(),
            customerProfileResponse.firstName()
        );
        assertEquals(
            createCustomerRequest.lastName(),
            customerProfileResponse.lastName()
        );
        assertEquals(
            createCustomerRequest.birthDate(),
            customerProfileResponse.birthDate()
        );
        assertEquals(createCustomerRequest.gender(), customerProfileResponse.gender());
        assertEquals(createCustomerRequest.customerId(), customerProfileResponse.idul());
    }

    private void createCustomerAndCheckStatusCreated(
        CreateCustomerProfileRequest createCustomerProfileRequest
    ) {
        given()
            .contentType(CONTENT_TYPE)
            .body(createCustomerProfileRequest)
            .when()
            .post(CUSTOMERS_PATH)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private void addCreditCardAndCheckStatusNoContent(
        AddCreditCardRequest addCreditCardRequest
    ) {
        given()
            .contentType(CONTENT_TYPE)
            .body(addCreditCardRequest)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        CUSTOMER_CREDENTIAL_ID,
                        EXPIRATION_DELAY_IN_MS,
                        Role.CUSTOMER
                    )
                )
            )
            .when()
            .post(CREDIT_CARD_PATH)
            .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    private void createCustomer() {
        CreateCustomerProfileRequest createCustomerProfileRequest =
            createCustomerProfileRequestFixture.build();
        createCustomerAndCheckStatusCreated(createCustomerProfileRequest);
    }
}

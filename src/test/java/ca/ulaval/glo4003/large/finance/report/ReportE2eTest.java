package ca.ulaval.glo4003.large.finance.report;

import static ca.ulaval.glo4003.RepulServer.BASE_URI;
import static ca.ulaval.glo4003.constant.Constants.Auth.AUTHORIZATION_HEADER_NAME;
import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.setClock;
import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.context.resource.ResourceConfigFactory;
import ca.ulaval.glo4003.fixture.request.AddCreditCardRequestFixture;
import ca.ulaval.glo4003.fixture.request.ConfirmMealKitRequestFixture;
import ca.ulaval.glo4003.fixture.request.CreateCustomerProfileRequestFixture;
import ca.ulaval.glo4003.fixture.request.CreateSubscriptionRequestFixture;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.api.customer.dto.request.CreateCustomerProfileRequest;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import ca.ulaval.glo4003.repul.auth.infra.credential.InMemoryCredentialRepository;
import ca.ulaval.glo4003.repul.auth.infra.token.JWTAuth;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ReportResponse;
import ca.ulaval.glo4003.repul.payment.api.dto.request.AddCreditCardRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.ConfirmMealKitRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.CreateSubscriptionRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionsResponse;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportE2eTest {

    private static final EmailAddress ADMIN_EMAIL = new EmailAddress("admin@email.com");
    private static final LocalDate FALL_SEMESTER = LocalDate.of(2023, 10, 10);
    private static final String CONTENT_TYPE = "application/json";
    private static final String WINTER_SEMESTER_CODE = "A23";
    private static final String AUTHORIZATION_PREFIX = "Bearer %s";
    private static final String CUSTOMER_ID_STRING = "ralol03";
    private static final CustomerProfileId CUSTOMER_ID = new CustomerProfileId(
        CUSTOMER_ID_STRING
    );
    private static final CredentialId CUSTOMER_CREDENTIAL_ID = new CredentialId(
        CUSTOMER_ID
    );
    private static final String REPORT_PATH = "/report";
    private static final String CUSTOMERS_PATH = "/customers";
    private static final String CREDIT_CARD_PATH = String.format(
        "/clients/%s/credit-card",
        CUSTOMER_ID_STRING
    );
    private static final String SUBSCRIPTION_PATH = String.format(
        "subscribers/%s/recurring-subscriptions",
        CUSTOMER_ID_STRING
    );
    private static final String GET_SUBSCRIPTION_PATH = String.format(
        "subscribers/%s",
        CUSTOMER_ID_STRING
    );

    private final CreateCustomerProfileRequestFixture createCustomerProfileRequestFixture =
        new CreateCustomerProfileRequestFixture();
    private final AddCreditCardRequestFixture addCreditCardRequestFixture =
        new AddCreditCardRequestFixture();
    private final ConfirmMealKitRequestFixture confirmMealKitRequestFixture =
        new ConfirmMealKitRequestFixture();
    private final CreateSubscriptionRequestFixture createSubscriptionRequestFixture =
        new CreateSubscriptionRequestFixture();

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
        RestAssured.urlEncodingEnabled = false;
        ResourceConfig config = new ResourceConfigFactory().create();

        server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
    }

    @AfterEach
    void tearDown() throws Exception {
        RestAssured.reset();
        server.stop();
    }

    @Test
    void noSemesterCode_getReport_returnsCurrentSemesterReport() {
        setClock(FALL_SEMESTER);

        given()
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
            .get(REPORT_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .as(ReportResponse.class);
    }

    @Test
    void semesterCode_getReport_returnsSemesterReport() {
        given()
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
            .queryParam("semesterCode", WINTER_SEMESTER_CODE)
            .when()
            .get(REPORT_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .as(ReportResponse.class);
    }

    @Test
    void confirmedMealKit_getReport_returnsSemesterReportWithExpectedTotal() {
        createCustomer();
        addCreditCard();
        addSubscription();
        String subscriptionId = getSubscriptionId();
        confirmMealKit(subscriptionId);

        given()
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
            .queryParam("semesterCode", WINTER_SEMESTER_CODE)
            .when()
            .get(REPORT_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .as(ReportResponse.class);
    }

    private void createCustomer() {
        CreateCustomerProfileRequest createCustomerProfileRequest =
            createCustomerProfileRequestFixture.build();
        createCustomerAndCheckStatus(createCustomerProfileRequest);
    }

    private void addCreditCard() {
        AddCreditCardRequest addCreditCardRequest = addCreditCardRequestFixture.build();
        addCreditCardAndCheckStatus(addCreditCardRequest);
    }

    private void addSubscription() {
        CreateSubscriptionRequest createSubscriptionRequest =
            createSubscriptionRequestFixture.withStartDate("2024-01-28").build();
        addSubscriptionAndCheckStatus(createSubscriptionRequest);
    }

    private void confirmMealKit(String subscriptionId) {
        ConfirmMealKitRequest confirmMealKitRequest =
            confirmMealKitRequestFixture.build();
        confirmMealKitAndCheckStatus(confirmMealKitRequest, subscriptionId);
    }

    private void confirmMealKitAndCheckStatus(
        ConfirmMealKitRequest confirmMealKitRequest,
        String subscriptionId
    ) {
        String confirmPath = String.format(
            "/subscribers/%s/recurring-subscriptions/%s:confirm",
            CUSTOMER_ID_STRING,
            subscriptionId
        );

        given()
            .contentType(CONTENT_TYPE)
            .body(confirmMealKitRequest)
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
            .post(confirmPath)
            .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    private String getSubscriptionId() {
        SubscriptionsResponse response = given()
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
            .get(GET_SUBSCRIPTION_PATH)
            .as(SubscriptionsResponse.class);

        return response.subscriptions().get(0).id();
    }

    private void addSubscriptionAndCheckStatus(
        CreateSubscriptionRequest createSubscriptionRequest
    ) {
        given()
            .contentType(CONTENT_TYPE)
            .body(createSubscriptionRequest)
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
            .post(SUBSCRIPTION_PATH)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private void createCustomerAndCheckStatus(
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

    private void addCreditCardAndCheckStatus(AddCreditCardRequest addCreditCardRequest) {
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
}

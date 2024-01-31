package ca.ulaval.glo4003.large.finance.billing;

import static ca.ulaval.glo4003.RepulServer.BASE_URI;
import static ca.ulaval.glo4003.constant.Constants.Auth.AUTHORIZATION_HEADER_NAME;
import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.resetClock;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.setClock;
import static io.restassured.RestAssured.given;

import ca.ulaval.glo4003.context.resource.ResourceConfigFactory;
import ca.ulaval.glo4003.fixture.request.*;
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
import ca.ulaval.glo4003.repul.finance.api.billing.dto.request.MealKitBillRequest;
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

public class BillE2eTest {

    private static final EmailAddress ADMIN_EMAIL = new EmailAddress("admin@email.com");
    private static final String CUSTOMER_PROFILE_ID_STRING = "ralol03";
    private static final CustomerProfileId CUSTOMER_PROFILE_ID = new CustomerProfileId(
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final CredentialId CUSTOMER_PROFILE_CREDENTIAL_ID = new CredentialId(
        CUSTOMER_PROFILE_ID
    );
    private static final String CUSTOMERS_PATH = "/customers";
    private static final String SUBSCRIBERS_PATH = "/subscribers";
    private static final String GET_SUBSCRIPTIONS_PATH = String.format(
        "%s/%s",
        SUBSCRIBERS_PATH,
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String POST_CUSTOMER_SUBSCRIPTIONS_PATH = String.format(
        "/subscribers/%s/recurring-subscriptions",
        CUSTOMER_PROFILE_ID_STRING
    );
    public static final String CONFIRM_MEAL_KIT_PATH_FORMAT =
        "/subscribers/%s/recurring-subscriptions/%s:confirm";
    private static final String CLIENT_CREDIT_CARD_PATH = String.format(
        "/clients/%s/credit-card",
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String CONTENT_TYPE = "application/json";
    private static final String BILLS_PATH = "/bills";
    private static final String AUTHORIZATION_PREFIX = "Bearer %s";
    public static final LocalDate FALL_SEMESTER = LocalDate.of(2023, 10, 10);
    public static final String IN_FALL_SEMESTER_DATE = "2023-10-11";

    private static CredentialId adminCredentialId;
    private final CreateCustomerProfileRequestFixture createCustomerProfileRequestFixture =
        new CreateCustomerProfileRequestFixture();
    private final CreateSubscriptionRequestFixture createSubscriptionRequestFixture =
        new CreateSubscriptionRequestFixture();
    private final ConfirmMealKitRequestFixture confirmMealKitRequestFixture =
        new ConfirmMealKitRequestFixture();
    private final AddCreditCardRequestFixture addCreditCardRequestFixture =
        new AddCreditCardRequestFixture();
    private final MealKitBillRequestFixture mealKitBillRequestFixture =
        new MealKitBillRequestFixture();

    private final TokenAuth tokenAuth = new JWTAuth();

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
        setClock(FALL_SEMESTER);

        server = JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);
    }

    @AfterEach
    void tearDown() throws Exception {
        RestAssured.reset();
        resetClock();
        server.stop();
    }

    @Test
    void billMealKit_returnsNoContent() {
        createCustomer();
        addSubscriptionToCustomer();
        addCreditCardToCustomer();
        String subscriptionId = getSubscriptionId();
        confirmMealKitAndCheckStatusNoContent(subscriptionId, true);

        addBillCheckStatusNoContent(subscriptionId);
    }

    private void addBillCheckStatusNoContent(String productId) {
        MealKitBillRequest mealKitBillRequest = mealKitBillRequestFixture
            .withCustomerId(CUSTOMER_PROFILE_ID_STRING)
            .withProductId(productId)
            .build();

        given()
            .contentType(CONTENT_TYPE)
            .body(mealKitBillRequest)
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
            .post(BILLS_PATH)
            .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    private void addSubscriptionToCustomer() {
        CreateSubscriptionRequest createSubscriptionRequest =
            createSubscriptionRequestFixture.withStartDate(IN_FALL_SEMESTER_DATE).build();

        given()
            .contentType(CONTENT_TYPE)
            .body(createSubscriptionRequest)
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
            .post(POST_CUSTOMER_SUBSCRIPTIONS_PATH)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private String getConfirmMealKitPath(String subscriptionId) {
        return String.format(
            CONFIRM_MEAL_KIT_PATH_FORMAT,
            CUSTOMER_PROFILE_ID,
            subscriptionId
        );
    }

    private String getSubscriptionId() {
        SubscriptionsResponse response = given()
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
            .get(GET_SUBSCRIPTIONS_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .as(SubscriptionsResponse.class);

        return response.subscriptions().get(0).id();
    }

    private void createCustomer() {
        CreateCustomerProfileRequest CreateCustomerProfileRequest =
            createCustomerProfileRequestFixture.build();

        given()
            .contentType(CONTENT_TYPE)
            .body(CreateCustomerProfileRequest)
            .when()
            .post(CUSTOMERS_PATH)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private void confirmMealKitAndCheckStatusNoContent(
        String subscriptionId,
        boolean isAccepted
    ) {
        ConfirmMealKitRequest confirmMealKitRequest = confirmMealKitRequestFixture
            .withAcceptKit(isAccepted)
            .build();

        given()
            .contentType(CONTENT_TYPE)
            .body(confirmMealKitRequest)
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
            .post(getConfirmMealKitPath(subscriptionId))
            .then()
            .statusCode(Response.Status.NO_CONTENT.getStatusCode());
    }

    private void addCreditCardToCustomer() {
        AddCreditCardRequest AddCreditCardRequest = addCreditCardRequestFixture.build();

        given()
            .contentType(CONTENT_TYPE)
            .body(AddCreditCardRequest)
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
}

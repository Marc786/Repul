package ca.ulaval.glo4003.large.subscription;

import static ca.ulaval.glo4003.RepulServer.BASE_URI;
import static ca.ulaval.glo4003.constant.Constants.Auth.AUTHORIZATION_HEADER_NAME;
import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.resetClock;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.setClock;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.context.resource.ResourceConfigFactory;
import ca.ulaval.glo4003.fixture.request.*;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.account.api.customer.dto.request.CreateCustomerProfileRequest;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import ca.ulaval.glo4003.repul.auth.infra.token.JWTAuth;
import ca.ulaval.glo4003.repul.payment.api.dto.request.AddCreditCardRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.ConfirmMealKitRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.CreateSubscriptionRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.MealKitResponse;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionsResponse;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionE2eTest {

    private static final String CUSTOMER_PROFILE_ID_STRING = "ralol03";
    private static final CustomerProfileId CUSTOMER_PROFILE_ID = new CustomerProfileId(
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final CredentialId CUSTOMER_PROFILE_CREDENTIAL_ID = new CredentialId(
        CUSTOMER_PROFILE_ID
    );
    private static final String EMAIL = "raph03@ulaval.ca";
    private static final String STUDENT_CARD_NUMBER = "330303030";
    private static final String CONTENT_TYPE = "application/json";
    private static final String CUSTOMERS_PATH = "/customers";
    private static final String SUBSCRIBERS_PATH = "/subscribers";
    private static final String RECURRING_SUBSCRIPTIONS_PATH = String.format(
        "%s/%s/recurring-subscriptions",
        SUBSCRIBERS_PATH,
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String SPORADIC_SUBSCRIPTIONS_PATH = String.format(
        "%s/%s/sporadic-subscriptions",
        SUBSCRIBERS_PATH,
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String GET_SUBSCRIPTIONS_PATH = String.format(
        "%s/%s",
        SUBSCRIBERS_PATH,
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String GET_MEAL_KITS_PATH = String.format(
        "/subscribers/%s/meal-kits",
        CUSTOMER_PROFILE_ID_STRING
    );
    public static final String CONFIRM_RECURRING_MEAL_KIT_PATH_FORMAT =
        "/subscribers/%s/recurring-subscriptions/%s:confirm";
    public static final String CONFIRM_SPORADIC_MEAL_KIT_PATH_FORMAT =
        "/subscribers/%s/sporadic-subscriptions/%s:confirm";
    private static final String CLIENT_CREDIT_CARD_PATH = String.format(
        "/clients/%s/credit-card",
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String AUTHORIZATION_PREFIX = "Bearer %s";

    private static final PickupPointLocation PICKUP_POINT_LOCATION =
        PickupPointLocation.PEPS;
    public static final LocalDate FALL_SEMESTER = LocalDate.of(2023, 10, 10);
    public static final String IN_FALL_SEMESTER_DATE = "2023-10-11";

    private final CreateCustomerProfileRequestFixture createCustomerProfileRequestFixture =
        new CreateCustomerProfileRequestFixture();
    private final CreateSubscriptionRequestFixture createSubscriptionRequestFixture =
        new CreateSubscriptionRequestFixture();
    private final ConfirmMealKitRequestFixture confirmMealKitRequestFixture =
        new ConfirmMealKitRequestFixture();
    private final AddCreditCardRequestFixture addCreditCardRequestFixture =
        new AddCreditCardRequestFixture();

    private final TokenAuth tokenAuth = new JWTAuth();

    private Server server;

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
    void createRecurringSubscription_returnsCreated() {
        createCustomer();

        createRecurringSubscriptionAndCheckStatusCreated();
    }

    @Test
    void createCustomerAndRecurringSubscription_getSubscriptionsById_returnsDetailsInBody() {
        createCustomer();
        createRecurringSubscriptionAndCheckStatusCreated();

        SubscriptionsResponse subscriptionsResponse = getSubscriptionsByIdResponse();

        assertFalse(subscriptionsResponse.subscriptions().isEmpty());
    }

    @Test
    void confirmRecurringMealKit_returnsNoContent() {
        String subscriptionId = createCustomerAddRecurringSubscriptionAndReturnId();
        addCreditCardToCustomer();

        confirmRecurringMealKitCheckStatusNoContent(subscriptionId, true);
    }

    @Test
    void noCreditCard_confirmRecurringMealKit_returnsNotFound() {
        String subscriptionId = createCustomerAddRecurringSubscriptionAndReturnId();

        confirmRecurringMealKitCheckStatusNotFound(subscriptionId, true);
    }

    @Test
    void refuseRecurringMealKit_returnsNoContent() {
        String subscriptionId = createCustomerAddRecurringSubscriptionAndReturnId();

        confirmRecurringMealKitCheckStatusNoContent(subscriptionId, false);
    }

    @Test
    void getRecurringMealKit_returnsOk() {
        createCustomerAddRecurringSubscription();

        getMealKitCheckStatusOk();
    }

    @Test
    void confirmedRecurringMealKit_getMealKit_returnsMealKit() {
        int expectedMealKitCount = 1;
        String subscriptionId = createCustomerAddRecurringSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmRecurringMealKitCheckStatusNoContent(subscriptionId, true);

        MealKitResponse[] mealKitResponses = given()
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
            .get(GET_MEAL_KITS_PATH)
            .as(MealKitResponse[].class);

        assertEquals(expectedMealKitCount, mealKitResponses.length);
    }

    @Test
    void createSporadicSubscription_returnsCreated() {
        createCustomer();

        createSporadicSubscriptionAndCheckStatusCreated();
    }

    @Test
    void createCustomerAndSporadicSubscription_getSubscriptionsById_returnsDetailsInBody() {
        createCustomer();
        createSporadicSubscriptionAndCheckStatusCreated();

        SubscriptionsResponse subscriptionsResponse = getSubscriptionsByIdResponse();

        assertFalse(subscriptionsResponse.subscriptions().isEmpty());
    }

    @Test
    void confirmSporadicMealKit_returnsNoContent() {
        String subscriptionId = createCustomerAddSporadicSubscriptionAndReturnId();
        addCreditCardToCustomer();

        confirmSporadicMealKitCheckStatusNoContent(subscriptionId);
    }

    @Test
    void noCreditCard_confirmSporadicMealKit_returnsNotFound() {
        String subscriptionId = createCustomerAddSporadicSubscriptionAndReturnId();

        confirmSporadicMealKitCheckStatusNotFound(subscriptionId);
    }

    @Test
    void getSporadicMealKit_returnsOk() {
        createCustomerAddSporadicSubscription();
        addCreditCardToCustomer();

        getMealKitCheckStatusOk();
    }

    @Test
    void confirmedSporadicMealKit_getSporadicMealKit_returnsMealKit() {
        String subscriptionId = createCustomerAddSporadicSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmSporadicMealKitCheckStatusNoContent(subscriptionId);

        MealKitResponse[] mealKitResponses = given()
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
            .get(GET_MEAL_KITS_PATH)
            .as(MealKitResponse[].class);

        assertNotEquals(0, mealKitResponses.length);
    }

    private SubscriptionsResponse getSubscriptionsByIdResponse() {
        return given()
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
    }

    private void createRecurringSubscriptionAndCheckStatusCreated() {
        createSubscriptionAndCheckStatusCreated(RECURRING_SUBSCRIPTIONS_PATH);
    }

    private void createSporadicSubscriptionAndCheckStatusCreated() {
        createSubscriptionAndCheckStatusCreated(SPORADIC_SUBSCRIPTIONS_PATH);
    }

    private void createSubscriptionAndCheckStatusCreated(String path) {
        CreateSubscriptionRequest createSubscriptionRequest =
            createSubscriptionRequestFixture
                .withLocation(PICKUP_POINT_LOCATION.toString())
                .withStartDate(IN_FALL_SEMESTER_DATE)
                .build();

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
            .post(path)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private void createCustomer() {
        CreateCustomerProfileRequest CreateCustomerProfileRequest =
            createCustomerProfileRequestFixture
                .withCustomerProfileId(CUSTOMER_PROFILE_ID_STRING)
                .withEmail(EMAIL)
                .withStudentCardNumber(STUDENT_CARD_NUMBER)
                .build();

        given()
            .contentType(CONTENT_TYPE)
            .body(CreateCustomerProfileRequest)
            .when()
            .post(CUSTOMERS_PATH)
            .then()
            .statusCode(Response.Status.CREATED.getStatusCode());
    }

    private void getMealKitCheckStatusOk() {
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
            .get(GET_MEAL_KITS_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    private void confirmRecurringMealKitCheckStatusNoContent(
        String subscriptionId,
        boolean isAccepted
    ) {
        confirmMealKitAndCheckStatusNoContent(
            getConfirmRecurringMealKitPath(subscriptionId),
            isAccepted,
            Response.Status.NO_CONTENT
        );
    }

    private void confirmSporadicMealKitCheckStatusNoContent(String subscriptionId) {
        confirmMealKitAndCheckStatusNoContent(
            getConfirmSporadicMealKitPath(subscriptionId),
            true,
            Response.Status.NO_CONTENT
        );
    }

    private void confirmRecurringMealKitCheckStatusNotFound(
        String subscriptionId,
        boolean isAccepted
    ) {
        confirmMealKitAndCheckStatusNoContent(
            getConfirmRecurringMealKitPath(subscriptionId),
            isAccepted,
            Response.Status.NOT_FOUND
        );
    }

    private void confirmSporadicMealKitCheckStatusNotFound(String subscriptionId) {
        confirmMealKitAndCheckStatusNoContent(
            getConfirmSporadicMealKitPath(subscriptionId),
            true,
            Response.Status.NOT_FOUND
        );
    }

    private void confirmMealKitAndCheckStatusNoContent(
        String path,
        boolean isAccepted,
        Response.Status expectedStatus
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
            .post(path)
            .then()
            .statusCode(expectedStatus.getStatusCode());
    }

    private void createCustomerAddRecurringSubscription() {
        createCustomer();

        createRecurringSubscriptionAndCheckStatusCreated();
    }

    private void createCustomerAddSporadicSubscription() {
        createCustomer();

        createSporadicSubscriptionAndCheckStatusCreated();
    }

    private String getConfirmRecurringMealKitPath(String subscriptionId) {
        return String.format(
            CONFIRM_RECURRING_MEAL_KIT_PATH_FORMAT,
            CUSTOMER_PROFILE_ID,
            subscriptionId
        );
    }

    private String getConfirmSporadicMealKitPath(String subscriptionId) {
        return String.format(
            CONFIRM_SPORADIC_MEAL_KIT_PATH_FORMAT,
            CUSTOMER_PROFILE_ID,
            subscriptionId
        );
    }

    private String createCustomerAddRecurringSubscriptionAndReturnId() {
        createCustomerAddRecurringSubscription();

        return getSubscriptionId();
    }

    private String createCustomerAddSporadicSubscriptionAndReturnId() {
        createCustomerAddSporadicSubscription();

        return getSubscriptionId();
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

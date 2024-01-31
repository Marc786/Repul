package ca.ulaval.glo4003.large.kitchen;

import static ca.ulaval.glo4003.RepulServer.BASE_URI;
import static ca.ulaval.glo4003.constant.Constants.Auth.AUTHORIZATION_HEADER_NAME;
import static ca.ulaval.glo4003.constant.Constants.Auth.EXPIRATION_DELAY_IN_MS;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.resetClock;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.setClock;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.context.resource.ResourceConfigFactory;
import ca.ulaval.glo4003.fixture.request.*;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.api.cook.dto.request.CreateCookProfileRequest;
import ca.ulaval.glo4003.repul.account.api.customer.dto.request.CreateCustomerProfileRequest;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.token.TokenAuth;
import ca.ulaval.glo4003.repul.auth.infra.credential.InMemoryCredentialRepository;
import ca.ulaval.glo4003.repul.auth.infra.token.JWTAuth;
import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.request.MealIdsRequest;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.*;
import ca.ulaval.glo4003.repul.payment.api.dto.request.AddCreditCardRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.ConfirmMealKitRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.CreateSubscriptionRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.MealKitResponse;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionsResponse;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKitId;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KitchenE2eTest {

    private static final EmailAddress ADMIN_EMAIL = new EmailAddress("admin@email.com");
    private static final String CUSTOMER_PROFILE_ID_STRING = "ralol03";
    private static final CustomerProfileId CUSTOMER_PROFILE_ID = new CustomerProfileId(
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final CredentialId CUSTOMER_PROFILE_CREDENTIAL_ID = new CredentialId(
        CUSTOMER_PROFILE_ID
    );
    private static final String EMAIL = "ralol03@ulaval.ca";
    private static final String STUDENT_CARD_NUMBER = "789456123";
    private static final String CONTENT_TYPE = "application/json";
    private static final String CUSTOMERS_PATH = "/customers";
    private static final String COOKS_PATH = "/cooks";
    private static final String GET_CUSTOMER_SUBSCRIPTIONS_PATH = String.format(
        "/subscribers/%s",
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String POST_CUSTOMER_SUBSCRIPTIONS_PATH = String.format(
        "/subscribers/%s/recurring-subscriptions",
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String GET_MEAL_KITS_PATH = String.format(
        "/subscribers/%s/meal-kits",
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String END_DATE = "2024-01-01";
    private static final String GET_MEAL_KITS_TO_PREPARE_PATH = String.format(
        "/meals?endDate=%s",
        END_DATE
    );
    private static final String GET_MEAL_KITS_TO_PREPARE_INGREDIENTS_PATH = String.format(
        "meals/ingredients?endDate=%s",
        END_DATE
    );
    private static final String CLIENT_CREDIT_CARD_PATH = String.format(
        "/clients/%s/credit-card",
        CUSTOMER_PROFILE_ID_STRING
    );
    private static final String AUTHORIZATION_PREFIX = "Bearer %s";
    private static final String ACTION_ASSIGN = "assign";
    private static final String ACTION_UNASSIGN = "unassign";
    private static final String ACTION_ASSEMBLE = "assemble";
    public static final String CONFIRM_MEAL_KIT_PATH_FORMAT =
        "/subscribers/%s/recurring-subscriptions/%s:confirm";
    public static final String POST_COOK_ACTION_PATH_FORMAT = "/cooks/%s/meals:%s";
    public static final LocalDate FALL_SEMESTER = LocalDate.of(2023, 10, 10);
    public static final String IN_FALL_SEMESTER_DATE = "2023-10-11";

    private final CreateCustomerProfileRequestFixture createCustomerProfileRequestFixture =
        new CreateCustomerProfileRequestFixture();
    private final CreateSubscriptionRequestFixture createSubscriptionRequestFixture =
        new CreateSubscriptionRequestFixture();
    private final ConfirmMealKitRequestFixture confirmMealKitRequestFixture =
        new ConfirmMealKitRequestFixture();
    private final MealIdsRequestFixture MealIdsRequestFixture =
        new MealIdsRequestFixture();
    private final AddCreditCardRequestFixture AddCreditCardRequestFixture =
        new AddCreditCardRequestFixture();
    private final CreateCookProfileRequestFixture CreateCookProfileRequestFixture =
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
    void confirmedMealKit_cookGetMealKitsToPrepareIngredients_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);

        CredentialId cookCredentialId = createCookAndGetCredentialId();
        given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        cookCredentialId,
                        EXPIRATION_DELAY_IN_MS,
                        Role.COOK
                    )
                )
            )
            .when()
            .get(GET_MEAL_KITS_TO_PREPARE_INGREDIENTS_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void confirmedMealKit_cookGetMealKitsToPrepareIngredients_returnsMealKitIngredients() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);

        CredentialId cookCredentialId = createCookAndGetCredentialId();
        IngredientsResponse ingredientsResponse = given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        cookCredentialId,
                        EXPIRATION_DELAY_IN_MS,
                        Role.COOK
                    )
                )
            )
            .when()
            .get(GET_MEAL_KITS_TO_PREPARE_INGREDIENTS_PATH)
            .as(IngredientsResponse.class);

        assertFalse(ingredientsResponse.ingredients().isEmpty());
    }

    @Test
    void confirmedMealKit_cookGetMealKitsToPrepare_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);

        CredentialId cookCredentialId = createCookAndGetCredentialId();
        given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        cookCredentialId,
                        EXPIRATION_DELAY_IN_MS,
                        Role.COOK
                    )
                )
            )
            .when()
            .get(GET_MEAL_KITS_TO_PREPARE_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    void confirmedMealKit_cookGetMealKitsToPrepare_returnsMealKit() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);

        CredentialId cookCredentialId = createCookAndGetCredentialId();
        MealsResponse mealKitResponses = given()
            .contentType(CONTENT_TYPE)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        cookCredentialId,
                        EXPIRATION_DELAY_IN_MS,
                        Role.COOK
                    )
                )
            )
            .when()
            .get(GET_MEAL_KITS_TO_PREPARE_PATH)
            .as(MealsResponse.class);

        assertFalse(mealKitResponses.mealResponses().isEmpty());
    }

    @Test
    void confirmedMealKit_assignMealKitToValidCook_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);
        MealKitId mealKitId = getMealKitId();
        CredentialId cookCredentialId = createCookAndGetCredentialId();

        assignMealKitsToCookReturnsOk(cookCredentialId, List.of(mealKitId));
    }

    @Test
    void refused_assignMealKitToValidCook_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, false);
        MealKitId mealKitId = getMealKitId();
        CredentialId cookCredentialId = createCookAndGetCredentialId();

        assignMealKitsToCookReturnsOk(cookCredentialId, List.of(mealKitId));
    }

    @Test
    void confirmedMealKit_unassignMealKit_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);
        MealKitId mealKitId = getMealKitId();
        CredentialId cookCredentialId = createCookAndGetCredentialId();

        unassignMealKitsToCookReturnsOk(cookCredentialId, List.of(mealKitId));
    }

    @Test
    void confirmedAssignedMealKit_unassignMealKitToValidCook_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);
        MealKitId mealKitId = getMealKitId();
        CredentialId cookCredentialId = createCookAndGetCredentialId();
        assignMealKitsToCookReturnsOk(cookCredentialId, List.of(mealKitId));

        unassignMealKitsToCookReturnsOk(cookCredentialId, List.of(mealKitId));
    }

    @Test
    void refused_unassignMealKitToValidCook_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, false);
        MealKitId mealKitId = getMealKitId();
        CredentialId cookCredentialId = createCookAndGetCredentialId();

        unassignMealKitsToCookReturnsOk(cookCredentialId, List.of(mealKitId));
    }

    @Test
    void confirmedAssignedMealKit_assembleMealKitToValidCook_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);
        MealKitId mealKitId = getMealKitId();
        CredentialId cookCredentialId = createCookAndGetCredentialId();
        assignMealKitsToCookReturnsOk(cookCredentialId, List.of(mealKitId));

        assembleMealKitsByCookReturnsOk(cookCredentialId, List.of(mealKitId));
    }

    @Test
    void confirmedUnAssignedMealKit_assembleMealKitToValidCook_returnsOk() {
        String subscriptionId = createCustomerAddSubscriptionAndReturnId();
        addCreditCardToCustomer();
        confirmMealKit(subscriptionId, true);
        MealKitId mealKitId = getMealKitId();
        CredentialId cookCredentialId = createCookAndGetCredentialId();

        assembleMealKitsByCookReturnsOk(cookCredentialId, List.of(mealKitId));
    }

    private void confirmMealKit(String subscriptionId, boolean isAccepted) {
        confirmMealKitAndCheckStatusNoContent(subscriptionId, isAccepted);
    }

    private MealKitId getMealKitId() {
        return new MealKitId(
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
                .as(MealKitResponse[].class)[0].id()
        );
    }

    private void assignMealKitsToCookReturnsOk(
        CredentialId cookCredentialId,
        List<MealKitId> mealKitIds
    ) {
        cookMealKitsPostRequestReturnsOk(ACTION_ASSIGN, cookCredentialId, mealKitIds);
    }

    private void unassignMealKitsToCookReturnsOk(
        CredentialId cookCredentialId,
        List<MealKitId> mealKitIds
    ) {
        cookMealKitsPostRequestReturnsOk(ACTION_UNASSIGN, cookCredentialId, mealKitIds);
    }

    private void assembleMealKitsByCookReturnsOk(
        CredentialId cookCredentialId,
        List<MealKitId> mealKitIds
    ) {
        cookMealKitsPostRequestReturnsOk(ACTION_ASSEMBLE, cookCredentialId, mealKitIds);
    }

    private void cookMealKitsPostRequestReturnsOk(
        String action,
        CredentialId cookCredentialId,
        List<MealKitId> mealKitIds
    ) {
        if (
            !Objects.equals(action, ACTION_ASSIGN) &&
            !Objects.equals(action, ACTION_UNASSIGN) &&
            !Objects.equals(action, ACTION_ASSEMBLE)
        ) {
            throw new IllegalArgumentException("Invalid path");
        }
        MealIdsRequest MealIdsRequest = MealIdsRequestFixture
            .withMealIds(mealKitIds.stream().map(MealKitId::toString).toList())
            .build();

        given()
            .contentType(CONTENT_TYPE)
            .body(MealIdsRequest)
            .header(
                AUTHORIZATION_HEADER_NAME,
                String.format(
                    AUTHORIZATION_PREFIX,
                    tokenAuth.generateToken(
                        cookCredentialId,
                        EXPIRATION_DELAY_IN_MS,
                        Role.COOK
                    )
                )
            )
            .when()
            .post(getPostCookActionPath(cookCredentialId.toString(), action))
            .then()
            .statusCode(Response.Status.OK.getStatusCode());
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

    private void createCustomerAddSubscription() {
        createCustomer();

        addSubscriptionToCustomer();
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

    private String getPostCookActionPath(String cookId, String action) {
        if (
            !Objects.equals(action, ACTION_ASSIGN) &&
            !Objects.equals(action, ACTION_UNASSIGN) &&
            !Objects.equals(action, ACTION_ASSEMBLE)
        ) {
            throw new IllegalArgumentException("Invalid action");
        }
        return String.format(POST_COOK_ACTION_PATH_FORMAT, cookId, action);
    }

    private String createCustomerAddSubscriptionAndReturnId() {
        createCustomerAddSubscription();

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
            .get(GET_CUSTOMER_SUBSCRIPTIONS_PATH)
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .extract()
            .as(SubscriptionsResponse.class);

        return response.subscriptions().get(0).id();
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

    private CredentialId createCookAndGetCredentialId() {
        CreateCookProfileRequest CreateCookProfileRequest =
            CreateCookProfileRequestFixture.build();

        io.restassured.response.Response response = given()
            .contentType(CONTENT_TYPE)
            .body(CreateCookProfileRequest)
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
            .statusCode(Response.Status.CREATED.getStatusCode())
            .extract()
            .response();

        String locationHeader = response.getHeader("Location");
        String cookIdString = locationHeader.substring(
            locationHeader.lastIndexOf('/') + 1
        );

        return new CredentialId(cookIdString);
    }

    private void addCreditCardToCustomer() {
        AddCreditCardRequest AddCreditCardRequest = AddCreditCardRequestFixture.build();

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

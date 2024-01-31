package ca.ulaval.glo4003.small.repul.subscription.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4003.fixture.request.CreateSubscriptionRequestFixture;
import ca.ulaval.glo4003.fixture.subscription.MealKitFixture;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.date.DateUtils;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.subscription.api.SubscriberResource;
import ca.ulaval.glo4003.repul.subscription.api.assembler.MealKitAssembler;
import ca.ulaval.glo4003.repul.subscription.api.assembler.SubscriptionResponseAssembler;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.ConfirmMealKitRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.CreateSubscriptionRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.MealKitResponse;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionsResponse;
import ca.ulaval.glo4003.repul.subscription.application.SubscriberService;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriberResourceTest {

    public static final SubscriptionId SUBSCRIPTION_ID = new SubscriptionId();
    public static final ConfirmMealKitRequest CONFIRM_MEAL_KIT_REQUEST =
        new ConfirmMealKitRequest(true);
    private static final String URI_PATH = "A/PATH/";
    private static final SubscriberId SUBSCRIBER_ID = new SubscriberId("idul");
    private static final List<Subscription> EMPTY_SUBSCRIPTION = List.of();
    private static final MealKitFixture mealKitFixture = new MealKitFixture();
    private static final List<MealKit> MEAL_KITS = List.of(mealKitFixture.build());
    public final MealKitAssembler mealKitAssembler = new MealKitAssembler();
    private final CreateSubscriptionRequestFixture createSubscriptionRequestFixture =
        new CreateSubscriptionRequestFixture();
    private final CreateSubscriptionRequest createSubscriptionRequest =
        createSubscriptionRequestFixture.build();
    private final SubscriptionResponseAssembler subscriptionResponseAssembler =
        new SubscriptionResponseAssembler();
    private final SubscriberService subscriberServiceMock = mock(SubscriberService.class);
    private final UriInfo mockUriInfo = mock(UriInfo.class);

    private SubscriberResource subscriberResource;

    @BeforeEach
    void setup() {
        subscriberResource = new SubscriberResource(subscriberServiceMock);
    }

    @Test
    void addSubscription_returnsCreatedWithPayload() {
        when(
            subscriberServiceMock.addRecurringSubscription(
                SUBSCRIBER_ID,
                createSubscriptionRequest.deliveringDayOfWeek(),
                DateUtils.formatDateToLocalDate(createSubscriptionRequest.startDate()),
                Frequency.fromString(createSubscriptionRequest.frequency()),
                PickupPointLocation.fromString(createSubscriptionRequest.location()),
                MealKitType.fromString(createSubscriptionRequest.mealKitType())
            )
        )
            .thenReturn(SUBSCRIPTION_ID);
        when(mockUriInfo.getAbsolutePathBuilder())
            .thenReturn(UriBuilder.fromPath(URI_PATH));

        Response actualResponse = subscriberResource.addSubscription(
            SUBSCRIBER_ID.toString(),
            createSubscriptionRequest,
            mockUriInfo
        );

        assertEquals(Response.Status.CREATED.getStatusCode(), actualResponse.getStatus());
        assertEquals(URI_PATH + SUBSCRIPTION_ID, actualResponse.getLocation().toString());
    }

    @Test
    void getSubscriptions_subscriptionsIsCalled() {
        SubscriptionsResponse expectedResponse = subscriptionResponseAssembler.toResponse(
            EMPTY_SUBSCRIPTION
        );
        when(subscriberServiceMock.getSubscriptions(SUBSCRIBER_ID))
            .thenReturn(EMPTY_SUBSCRIPTION);

        Response actualResponse = subscriberResource.getSubscriptions(
            SUBSCRIBER_ID.toString()
        );

        assertEquals(Response.Status.OK.getStatusCode(), actualResponse.getStatus());
        assertEquals(expectedResponse, actualResponse.getEntity());
    }

    @Test
    void confirmMealKit_confirmRecurringMealKitIsCalled() {
        Response actualResponse = subscriberResource.confirmMealKit(
            SUBSCRIBER_ID.toString(),
            SUBSCRIPTION_ID.toString(),
            CONFIRM_MEAL_KIT_REQUEST
        );

        verify(subscriberServiceMock)
            .confirmMealKit(
                SUBSCRIBER_ID,
                SUBSCRIPTION_ID,
                CONFIRM_MEAL_KIT_REQUEST.acceptKit()
            );
        assertEquals(
            Response.Status.NO_CONTENT.getStatusCode(),
            actualResponse.getStatus()
        );
    }

    @Test
    void getMealKits_mealKitsAreReturned() {
        when(subscriberServiceMock.getMealKits(SUBSCRIBER_ID)).thenReturn(MEAL_KITS);
        List<MealKitResponse> expectedResponse = mealKitAssembler.toResponse(MEAL_KITS);

        Response actualResponse = subscriberResource.getMealKits(
            SUBSCRIBER_ID.toString()
        );

        assertEquals(Response.Status.OK.getStatusCode(), actualResponse.getStatus());
        assertEquals(expectedResponse, actualResponse.getEntity());
    }

    @Test
    void addSporadicSubscription_returnsCreatedWithPayload() {
        when(subscriberServiceMock.addSporadicSubscription(SUBSCRIBER_ID))
            .thenReturn(SUBSCRIPTION_ID);
        when(mockUriInfo.getAbsolutePathBuilder())
            .thenReturn(UriBuilder.fromPath(URI_PATH));

        Response actualResponse = subscriberResource.addSporadicSubscription(
            SUBSCRIBER_ID.toString(),
            mockUriInfo
        );

        assertEquals(Response.Status.CREATED.getStatusCode(), actualResponse.getStatus());
        assertEquals(URI_PATH + SUBSCRIPTION_ID, actualResponse.getLocation().toString());
    }

    @Test
    void confirmSporadicMealKit_confirmSporadicMealKitIsCalled() {
        Response actualResponse = subscriberResource.confirmSporadicMealKit(
            SUBSCRIBER_ID.toString(),
            SUBSCRIPTION_ID.toString()
        );

        verify(subscriberServiceMock)
            .confirmMealKit(SUBSCRIBER_ID, SUBSCRIPTION_ID, true);
        assertEquals(
            Response.Status.NO_CONTENT.getStatusCode(),
            actualResponse.getStatus()
        );
    }
}

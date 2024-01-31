package ca.ulaval.glo4003.repul.subscription.api;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.date.DateUtils;
import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.middleware.auth.annotation.ACL;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.subscription.api.assembler.MealKitAssembler;
import ca.ulaval.glo4003.repul.subscription.api.assembler.SubscriptionResponseAssembler;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.ConfirmMealKitRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.request.CreateSubscriptionRequest;
import ca.ulaval.glo4003.repul.subscription.api.dto.response.SubscriptionsResponse;
import ca.ulaval.glo4003.repul.subscription.application.SubscriberService;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.Subscription;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.SubscriptionId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Path("/subscribers")
@Produces(MediaType.APPLICATION_JSON)
public class SubscriberResource {

    private final SubscriberService subscriberService;
    private final SubscriptionResponseAssembler subscriptionResponseAssembler;
    private final MealKitAssembler mealKitAssembler;

    public SubscriberResource(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
        this.subscriptionResponseAssembler = new SubscriptionResponseAssembler();
        this.mealKitAssembler = new MealKitAssembler();
    }

    @POST
    @Path("/{subscriberId}/recurring-subscriptions")
    @ACL(accountId = "subscriberId")
    @RBAC(roles = { Role.CUSTOMER })
    public Response addSubscription(
        @NotBlank @PathParam("subscriberId") String subscriberIdParam,
        @NotNull @Valid CreateSubscriptionRequest createSubscriptionRequest,
        @Context UriInfo uriInfo
    ) {
        SubscriberId subscriberId = new SubscriberId(subscriberIdParam);
        LocalDate subscriptionStartDate = DateUtils.formatDateToLocalDate(
            createSubscriptionRequest.startDate()
        );
        PickupPointLocation pickupPointLocation = PickupPointLocation.fromString(
            createSubscriptionRequest.location()
        );
        MealKitType mealKitType = MealKitType.fromString(
            createSubscriptionRequest.mealKitType()
        );
        DayOfWeek deliveringDayOfWeek = createSubscriptionRequest.deliveringDayOfWeek();
        Frequency deliveryFrequency = Frequency.fromString(
            createSubscriptionRequest.frequency()
        );

        SubscriptionId subscriptionId = subscriberService.addRecurringSubscription(
            subscriberId,
            deliveringDayOfWeek,
            subscriptionStartDate,
            deliveryFrequency,
            pickupPointLocation,
            mealKitType
        );

        return Response
            .created(
                uriInfo.getAbsolutePathBuilder().path(subscriptionId.toString()).build()
            )
            .build();
    }

    @POST
    @Path("/{subscriberId}/recurring-subscriptions/{subscriptionId}:confirm")
    @ACL(accountId = "subscriberId")
    @RBAC(roles = { Role.CUSTOMER })
    public Response confirmMealKit(
        @NotBlank @PathParam("subscriberId") String subscriberIdParam,
        @NotBlank @PathParam("subscriptionId") String subscriptionId,
        @NotNull @Valid ConfirmMealKitRequest confirmMealKitRequest
    ) {
        SubscriberId subscriberId = new SubscriberId(subscriberIdParam);
        boolean isAccepted = confirmMealKitRequest.acceptKit();

        subscriberService.confirmMealKit(
            subscriberId,
            new SubscriptionId(subscriptionId),
            isAccepted
        );

        return Response.noContent().build();
    }

    @POST
    @Path("/{subscriberId}/sporadic-subscriptions")
    @ACL(accountId = "subscriberId")
    @RBAC(roles = { Role.CUSTOMER })
    public Response addSporadicSubscription(
        @NotBlank @PathParam("subscriberId") String subscriberIdParam,
        @Context UriInfo uriInfo
    ) {
        SubscriberId subscriberId = new SubscriberId(subscriberIdParam);
        SubscriptionId subscriptionId = subscriberService.addSporadicSubscription(
            subscriberId
        );

        return Response
            .created(
                uriInfo.getAbsolutePathBuilder().path(subscriptionId.toString()).build()
            )
            .build();
    }

    @POST
    @Path("/{subscriberId}/sporadic-subscriptions/{subscriptionId}:confirm")
    @ACL(accountId = "subscriberId")
    @RBAC(roles = { Role.CUSTOMER })
    public Response confirmSporadicMealKit(
        @NotBlank @PathParam("subscriberId") String subscriberIdParam,
        @NotBlank @PathParam("subscriptionId") String subscriptionId
    ) {
        SubscriberId subscriberId = new SubscriberId(subscriberIdParam);

        subscriberService.confirmMealKit(
            subscriberId,
            new SubscriptionId(subscriptionId),
            true
        );

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{subscriberId}")
    @ACL(accountId = "subscriberId")
    @RBAC(roles = { Role.CUSTOMER })
    public Response getSubscriptions(
        @NotBlank @PathParam("subscriberId") String subscriberIdParam
    ) {
        SubscriberId subscriberId = new SubscriberId(subscriberIdParam);

        List<Subscription> subscriptions = subscriberService.getSubscriptions(
            subscriberId
        );

        SubscriptionsResponse subscriptionsResponse =
            subscriptionResponseAssembler.toResponse(subscriptions);

        return Response.ok(subscriptionsResponse).build();
    }

    @GET
    @Path("/{subscriberId}/meal-kits")
    @ACL(accountId = "subscriberId")
    @RBAC(roles = { Role.CUSTOMER })
    public Response getMealKits(
        @NotBlank @PathParam("subscriberId") String subscriberIdParam
    ) {
        SubscriberId subscriberId = new SubscriberId(subscriberIdParam);

        List<MealKit> mealKits = subscriberService.getMealKits(subscriberId);

        return Response.ok(mealKitAssembler.toResponse(mealKits)).build();
    }
}

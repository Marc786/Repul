package ca.ulaval.glo4003.repul.shipment.api;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup;
import static ca.ulaval.glo4003.constant.Constants.ShipmentOrigin.SHIPMENT_MAX_RANGE_DAY;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.shipment.api.assembler.ShipmentItemIdAssembler;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemsRequest;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.ConfirmedShipmentItemRequest;
import ca.ulaval.glo4003.repul.shipment.application.ShipmentService;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentId;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;

@Path("/shipments")
@Produces(MediaType.APPLICATION_JSON)
public class ShipmentResource {

    private final ShipmentService shipmentService;
    private final ShipmentItemIdAssembler shipmentItemIdAssembler;

    public ShipmentResource(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
        this.shipmentItemIdAssembler = new ShipmentItemIdAssembler();
    }

    @POST
    @Path("/create-shipment")
    @RBAC(roles = { Role.COOK })
    public Response createShipment(@Context UriInfo uriInfo) {
        LocalDate lowerBoundDate = LocalDate.now(ClockSetup.getClock());
        LocalDate upperBoundDate = lowerBoundDate.plusDays(SHIPMENT_MAX_RANGE_DAY);

        ShipmentId shipmentId = shipmentService.createShipment(
            lowerBoundDate,
            upperBoundDate
        );

        return Response
            .created(uriInfo.getAbsolutePathBuilder().path(shipmentId.toString()).build())
            .build();
    }

    public void addConfirmedShipmentItem(
        @NotNull @Valid ConfirmedShipmentItemRequest item
    ) {
        ShipmentItemId shipmentItemId = new ShipmentItemId(item.id());
        PickupPointLocation pickupPointLocation = PickupPointLocation.fromString(
            item.pickUpPointLocation()
        );
        LocalDate deliveryDate = LocalDate.parse(item.deliveryDate());

        shipmentService.addConfirmedShipmentItem(
            shipmentItemId,
            pickupPointLocation,
            deliveryDate
        );
    }

    public void assembleShipmentItems(
        @NotNull @Valid AssembledShipmentItemsRequest assembledShipmentItemsRequest
    ) {
        List<ShipmentItemId> shipmentItemIds = shipmentItemIdAssembler.toShipmentItemIds(
            assembledShipmentItemsRequest.shipmentItemsIds()
        );

        shipmentService.addAssembledShipmentItems(shipmentItemIds);
    }
}

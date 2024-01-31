package ca.ulaval.glo4003.small.repul.shipment.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.lib.pickup_point_location.PickupPointLocation;
import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemRequest;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemsRequest;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.ConfirmedShipmentItemRequest;
import ca.ulaval.glo4003.repul.shipment.application.ShipmentService;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentId;
import ca.ulaval.glo4003.repul.shipment.domain.ShipmentItemId;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ShipmentResourceTest {

    private static final ShipmentId SHIPMENT_ID = new ShipmentId();
    private static final String URI_PATH = "A/PATH/";
    private static final PickupPointLocation PICKUP_POINT_LOCATION =
        PickupPointLocation.VACHON;
    private static final String SHIPMENT_ITEM_ID = UUID.randomUUID().toString();
    private static final AssembledShipmentItemRequest ASSEMBLED_SHIPMENT_ITEM_REQUEST =
        new AssembledShipmentItemRequest(SHIPMENT_ITEM_ID);
    private static final AssembledShipmentItemsRequest ASSEMBLED_SHIPMENT_ITEMS_REQUEST =
        new AssembledShipmentItemsRequest(List.of(ASSEMBLED_SHIPMENT_ITEM_REQUEST));
    private static final String DELIVERY_DATE = LocalDate.of(2023, 1, 1).toString();
    private final ConfirmedShipmentItemRequest confirmedShipmentItemRequest =
        new ConfirmedShipmentItemRequest(
            SHIPMENT_ITEM_ID,
            PICKUP_POINT_LOCATION.toString(),
            DELIVERY_DATE
        );
    private final UriInfo uriInfoMock = mock(UriInfo.class);
    private final ShipmentService shipmentService = mock(ShipmentService.class);
    private final ShipmentResource shipmentResource = new ShipmentResource(
        shipmentService
    );

    @Test
    void createShipment_shipmentIsCreated() {
        when(shipmentService.createShipment(any(), any())).thenReturn(SHIPMENT_ID);
        when(uriInfoMock.getAbsolutePathBuilder())
            .thenReturn(UriBuilder.fromPath(URI_PATH));

        int expectedStatus = Response.Status.CREATED.getStatusCode();

        Response response = shipmentResource.createShipment(uriInfoMock);

        int actualStatus = response.getStatus();
        String actualLocation = response.getLocation().toString();
        assertEquals(expectedStatus, actualStatus);
        assertEquals(URI_PATH + SHIPMENT_ID, actualLocation);
    }

    @Test
    void addConfirmedShipmentItem_shipmentItemIsAdded() {
        ShipmentItemId expectedShipmentItemId = new ShipmentItemId(SHIPMENT_ITEM_ID);
        PickupPointLocation expectedPickupPointLocation = PickupPointLocation.fromString(
            PICKUP_POINT_LOCATION.toString()
        );
        LocalDate expectedDeliveryDate = LocalDate.parse(DELIVERY_DATE);

        shipmentResource.addConfirmedShipmentItem(confirmedShipmentItemRequest);

        verify(shipmentService)
            .addConfirmedShipmentItem(
                expectedShipmentItemId,
                expectedPickupPointLocation,
                expectedDeliveryDate
            );
    }

    @Test
    void addAssembledShipmentItem_shipmentItemIsAdded() {
        List<ShipmentItemId> expectedShipmentItemIds = List.of(
            new ShipmentItemId(SHIPMENT_ITEM_ID)
        );

        shipmentResource.assembleShipmentItems(ASSEMBLED_SHIPMENT_ITEMS_REQUEST);

        verify(shipmentService).addAssembledShipmentItems(expectedShipmentItemIds);
    }
}

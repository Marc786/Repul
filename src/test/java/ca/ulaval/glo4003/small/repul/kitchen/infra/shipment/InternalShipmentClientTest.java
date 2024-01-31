package ca.ulaval.glo4003.small.repul.kitchen.infra.shipment;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.infra.shipment.InternalShipmentClient;
import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.AssembledShipmentItemsRequest;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InternalShipmentClientTest {

    private static final String MEAL_ID_STRING = UUID.randomUUID().toString();
    private static final MealId MEAL_ID = new MealId(MEAL_ID_STRING);
    private static final List<MealId> MEAL_IDS = List.of(MEAL_ID);
    private static final ShipmentResource shipmentResourceMock = mock(
        ShipmentResource.class
    );
    private InternalShipmentClient internalShipmentClient;

    @BeforeEach
    void setup() {
        internalShipmentClient = new InternalShipmentClient(shipmentResourceMock);
    }

    @Test
    void addAssembledShipmentItem_itemIsAdded() {
        internalShipmentClient.addAssembledShipmentItem(MEAL_IDS);

        verify(shipmentResourceMock)
            .assembleShipmentItems(any(AssembledShipmentItemsRequest.class));
    }
}

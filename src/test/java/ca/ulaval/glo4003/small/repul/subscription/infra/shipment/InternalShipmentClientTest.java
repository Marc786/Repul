package ca.ulaval.glo4003.small.repul.subscription.infra.shipment;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.fixture.subscription.MealKitFixture;
import ca.ulaval.glo4003.repul.shipment.api.ShipmentResource;
import ca.ulaval.glo4003.repul.shipment.api.dto.request.ConfirmedShipmentItemRequest;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.infra.shipment.InternalShipmentClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InternalShipmentClientTest {

    private final MealKitFixture mealKitFixture = new MealKitFixture();
    private final ShipmentResource shipmentResourceMock = mock(ShipmentResource.class);

    private InternalShipmentClient internalShipmentClient;

    @BeforeEach
    void setup() {
        internalShipmentClient = new InternalShipmentClient(shipmentResourceMock);
    }

    @Test
    void addConfirmedShipmentItem_addConfirmedShipmentItemIsCalled() {
        MealKit mealKit = mealKitFixture.build();
        ConfirmedShipmentItemRequest expectedRequest = new ConfirmedShipmentItemRequest(
            mealKit.getId().toString(),
            mealKit.getPickupPointLocation().toString(),
            mealKit.getDeliveryDate().toString()
        );

        internalShipmentClient.addConfirmedMealKitShipment(mealKit);

        verify(shipmentResourceMock).addConfirmedShipmentItem(expectedRequest);
    }
}

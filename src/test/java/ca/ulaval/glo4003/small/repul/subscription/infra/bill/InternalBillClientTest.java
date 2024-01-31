package ca.ulaval.glo4003.small.repul.subscription.infra.bill;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.fixture.subscription.MealKitFixture;
import ca.ulaval.glo4003.repul.finance.api.billing.BillResource;
import ca.ulaval.glo4003.repul.finance.api.billing.dto.request.MealKitBillRequest;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;
import ca.ulaval.glo4003.repul.subscription.infra.bill.InternalBillClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InternalBillClientTest {

    private static final SubscriberId SUBSCRIBER_ID = new SubscriberId("123");
    private final MealKitFixture mealKitFixture = new MealKitFixture();
    private final BillResource billResourceMock = mock(BillResource.class);

    private InternalBillClient internalBillClient;

    @BeforeEach
    void setup() {
        internalBillClient = new InternalBillClient(billResourceMock);
    }

    @Test
    void bill_billMealKitIsCalled() {
        MealKit mealKit = mealKitFixture.build();
        MealKitBillRequest expectedRequest = new MealKitBillRequest(
            SUBSCRIBER_ID.toString(),
            mealKit.getId().toString(),
            mealKit.getDeliveryDate().toString(),
            mealKit.getMealKitType().toString()
        );

        internalBillClient.bill(SUBSCRIBER_ID, mealKit);

        verify(billResourceMock).billMealKit(expectedRequest);
    }
}

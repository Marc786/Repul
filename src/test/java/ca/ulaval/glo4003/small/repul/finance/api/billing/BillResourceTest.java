package ca.ulaval.glo4003.small.repul.finance.api.billing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.repul.finance.api.billing.BillResource;
import ca.ulaval.glo4003.repul.finance.api.billing.dto.request.MealKitBillRequest;
import ca.ulaval.glo4003.repul.finance.application.bill.BillService;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.ProductId;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BillResourceTest {

    private final BuyerId CUSTOMER_ID = new BuyerId("customerId");
    private final ProductId PRODUCT_ID = new ProductId("productId");
    private final LocalDate DELIVERY_DATE = LocalDate.of(2022, 1, 1);
    private final MealKitType MEAL_KIT_TYPE = MealKitType.STANDARD;
    private final BillService billServiceMock = mock(BillService.class);
    private final MealKitBillRequest mealKitBillRequest = new MealKitBillRequest(
        CUSTOMER_ID.toString(),
        PRODUCT_ID.toString(),
        DELIVERY_DATE.toString(),
        MEAL_KIT_TYPE.toString()
    );

    private BillResource billResource;

    @BeforeEach
    void setup() {
        billResource = new BillResource(billServiceMock);
    }

    @Test
    void billMealKit_serviceBillMealKitCalled() {
        billResource.billMealKit(mealKitBillRequest);

        verify(billServiceMock)
            .billMealKit(CUSTOMER_ID, PRODUCT_ID, MEAL_KIT_TYPE, DELIVERY_DATE);
    }
}

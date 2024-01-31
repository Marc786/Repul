package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.repul.finance.api.billing.dto.request.MealKitBillRequest;

public class MealKitBillRequestFixture {

    private String customerId = "customerId";
    private String productId = "productId";
    private final String deliveryDate = "2023-12-12";
    private final String mealKitType = MealKitType.STANDARD.toString();

    public MealKitBillRequest build() {
        return new MealKitBillRequest(customerId, productId, deliveryDate, mealKitType);
    }

    public MealKitBillRequestFixture withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public MealKitBillRequestFixture withProductId(String productId) {
        this.productId = productId;
        return this;
    }
}

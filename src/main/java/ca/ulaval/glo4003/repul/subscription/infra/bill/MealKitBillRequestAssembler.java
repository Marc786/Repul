package ca.ulaval.glo4003.repul.subscription.infra.bill;

import ca.ulaval.glo4003.repul.finance.api.billing.dto.request.MealKitBillRequest;

public class MealKitBillRequestAssembler {

    public MealKitBillRequest toRequest(
        String customerId,
        String productId,
        String deliveryDate,
        String mealKitType
    ) {
        return new MealKitBillRequest(customerId, productId, deliveryDate, mealKitType);
    }
}

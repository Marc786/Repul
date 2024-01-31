package ca.ulaval.glo4003.repul.subscription.infra.bill;

import ca.ulaval.glo4003.repul.finance.api.billing.BillResource;
import ca.ulaval.glo4003.repul.finance.api.billing.dto.request.MealKitBillRequest;
import ca.ulaval.glo4003.repul.subscription.domain.BillClient;
import ca.ulaval.glo4003.repul.subscription.domain.SubscriberId;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.meal_kit.MealKit;

public class InternalBillClient implements BillClient {

    private final BillResource billResource;
    private final MealKitBillRequestAssembler mealKitBillRequestAssembler =
        new MealKitBillRequestAssembler();

    public InternalBillClient(BillResource billResource) {
        this.billResource = billResource;
    }

    @Override
    public void bill(SubscriberId subscriberId, MealKit mealKit) {
        MealKitBillRequest mealKitBillRequest = mealKitBillRequestAssembler.toRequest(
            subscriberId.toString(),
            mealKit.getId().toString(),
            mealKit.getDeliveryDate().toString(),
            mealKit.getMealKitType().toString()
        );

        billResource.billMealKit(mealKitBillRequest);
    }
}

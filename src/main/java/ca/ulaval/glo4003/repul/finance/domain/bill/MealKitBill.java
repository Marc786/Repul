package ca.ulaval.glo4003.repul.finance.domain.bill;

import static ca.ulaval.glo4003.constant.Constants.Validator.Bill.*;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.ProductId;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class MealKitBill implements Bill {

    private final BillId billId;
    private final BuyerId buyerId;
    private final ProductId productId;
    private final Amount price;
    private final MealKitType mealKitType;
    private final LocalDate deliveryDate;

    public MealKitBill(
        BillId billId,
        BuyerId buyerId,
        ProductId productId,
        Amount price,
        MealKitType mealKitType,
        LocalDate deliveryDate
    ) {
        this.billId = billId;
        this.buyerId = buyerId;
        this.productId = productId;
        this.price = price;
        this.mealKitType = mealKitType;
        this.deliveryDate = deliveryDate;
    }

    public BillId getId() {
        return billId;
    }

    public BuyerId getCustomerId() {
        return buyerId;
    }

    @Override
    public BillDetail getDetail() {
        return new BillDetail(
            Map.of(
                DETAIL_CUSTOMER_ID_FIELD,
                buyerId.toString(),
                DETAIL_PRODUCT_ID_FIELD,
                productId.toString(),
                DETAIL_MEAL_KIT_TYPE_FIELD,
                mealKitType.toString(),
                DETAIL_DELIVERY_DATE_FIELD,
                deliveryDate.toString()
            )
        );
    }

    public Amount getPrice() {
        return price;
    }

    @Override
    public LocalDate getEffectiveDate() {
        return deliveryDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MealKitBill mealKitBill = (MealKitBill) obj;

        return billId.equals(mealKitBill.billId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(billId);
    }
}

package ca.ulaval.glo4003.fixture.finance;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.bill.MealKitBill;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.ProductId;
import java.time.LocalDate;

public class MealKitBillFixture {

    private BillId billId = new BillId();
    private BuyerId buyerId = new BuyerId("customerId");
    private ProductId productId = new ProductId("123");
    private Amount price = new Amount(100);
    private MealKitType mealKitType = MealKitType.STANDARD;
    private LocalDate deliveryDate = LocalDate.now();

    public MealKitBill build() {
        return new MealKitBill(
            billId,
            buyerId,
            productId,
            price,
            mealKitType,
            deliveryDate
        );
    }

    public MealKitBillFixture withBillId(BillId billId) {
        this.billId = billId;
        return this;
    }

    public MealKitBillFixture withCustomerId(BuyerId buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    public MealKitBillFixture withProductId(ProductId productId) {
        this.productId = productId;
        return this;
    }

    public MealKitBillFixture withPrice(Amount amount) {
        this.price = amount;
        return this;
    }

    public MealKitBillFixture withMealKitType(MealKitType mealKitType) {
        this.mealKitType = mealKitType;
        return this;
    }

    public MealKitBillFixture withDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }
}

package ca.ulaval.glo4003.repul.finance.application.bill;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.finder.MealKitPriceFinder;
import ca.ulaval.glo4003.repul.finance.domain.PaymentClient;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.domain.bill.MealKitBill;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.ProductId;
import java.time.LocalDate;

public class BillService {

    private final BillRepository billRepository;
    private final PaymentClient paymentClient;
    private final MealKitPriceFinder mealKitPriceFinder;

    public BillService(
        BillRepository billRepository,
        PaymentClient paymentClient,
        MealKitPriceFinder mealKitPriceFinder
    ) {
        this.billRepository = billRepository;
        this.paymentClient = paymentClient;
        this.mealKitPriceFinder = mealKitPriceFinder;
    }

    public void billMealKit(
        BuyerId buyerId,
        ProductId productId,
        MealKitType mealKitType,
        LocalDate deliveryDate
    ) {
        MealKitBill mealKitBill = new MealKitBill(
            new BillId(),
            buyerId,
            productId,
            mealKitPriceFinder.findPrice(mealKitType),
            mealKitType,
            deliveryDate
        );
        paymentClient.pay(mealKitBill);
        billRepository.save(mealKitBill);
    }
}

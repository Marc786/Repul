package ca.ulaval.glo4003.small.repul.finance.application.bill;

import static org.mockito.Mockito.*;

import ca.ulaval.glo4003.fixture.finance.MealKitBillFixture;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.finder.MealKitPriceFinder;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.application.bill.BillService;
import ca.ulaval.glo4003.repul.finance.domain.PaymentClient;
import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.domain.bill.MealKitBill;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.ProductId;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BillServiceTest {

    private final BillRepository billRepositoryMock = mock(BillRepository.class);
    private final PaymentClient paymentClientMock = mock(PaymentClient.class);
    private final MealKitPriceFinder mealKitPriceFinderMock = mock(
        MealKitPriceFinder.class
    );
    private final BuyerId CUSTOMER_ID = new BuyerId("ralol01");
    private final ProductId PRODUCT_ID = new ProductId("productId");
    private final Amount STANDARD_PRICE = new Amount(100.0);
    private final MealKitType MEAL_KIT_TYPE = MealKitType.STANDARD;
    private final LocalDate DELIVERY_DATE = LocalDate.now();
    private final MealKitBillFixture mealKitBillFixture = new MealKitBillFixture();
    private BillService billService;

    @BeforeEach
    void setup() {
        billService =
            new BillService(
                billRepositoryMock,
                paymentClientMock,
                mealKitPriceFinderMock
            );
    }

    @Test
    void billMealKit_payIsCalled() {
        MealKitBill expectedBill = mealKitBillFixture
            .withCustomerId(CUSTOMER_ID)
            .withProductId(PRODUCT_ID)
            .withPrice(STANDARD_PRICE)
            .withMealKitType(MEAL_KIT_TYPE)
            .withDeliveryDate(DELIVERY_DATE)
            .build();
        when(mealKitPriceFinderMock.findPrice(MEAL_KIT_TYPE)).thenReturn(STANDARD_PRICE);

        billService.billMealKit(CUSTOMER_ID, PRODUCT_ID, MEAL_KIT_TYPE, DELIVERY_DATE);

        verify(paymentClientMock)
            .pay(
                argThat(actualBill ->
                    isSameCustomer(actualBill, expectedBill) &&
                    isSamePrice(actualBill, expectedBill) &&
                    isSameReportDate(actualBill, expectedBill)
                )
            );
    }

    private boolean isSameCustomer(Bill actual, MealKitBill expected) {
        return actual.getCustomerId().equals(expected.getCustomerId());
    }

    private boolean isSamePrice(Bill actual, MealKitBill expected) {
        return actual.getPrice().equals(expected.getPrice());
    }

    private boolean isSameReportDate(Bill actual, MealKitBill expected) {
        return actual.getEffectiveDate().equals(expected.getEffectiveDate());
    }
}

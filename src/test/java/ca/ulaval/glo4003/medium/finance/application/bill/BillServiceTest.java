package ca.ulaval.glo4003.medium.finance.application.bill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.lib.catalog.MealKitCatalog;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.catalog.finder.MealKitPriceFinder;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.application.bill.BillService;
import ca.ulaval.glo4003.repul.finance.domain.PaymentClient;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.ProductId;
import ca.ulaval.glo4003.repul.finance.infra.InMemoryBillRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BillServiceTest {

    private final BuyerId CUSTOMER_ID = new BuyerId("ralol01");
    private final ProductId PRODUCT_ID = new ProductId("productId");
    private final MealKitType MEAL_KIT_TYPE = MealKitType.STANDARD;
    private final LocalDate DELIVERY_DATE = LocalDate.of(2023, 12, 25);
    private final LocalDate YESTERDAY = DELIVERY_DATE.minusDays(1);
    private final LocalDate TOMORROW = DELIVERY_DATE.plusDays(1);
    private final BillRepository billRepository = new InMemoryBillRepository();
    private final PaymentClient paymentClient = mock(PaymentClient.class);
    private final MealKitPriceFinder mealKitPriceFinder = new MealKitCatalog();
    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private BillService billService;

    @BeforeEach
    void setup() {
        billService = new BillService(billRepository, paymentClient, mealKitPriceFinder);
    }

    @Test
    void billMealKit_mealKitBillIsSaved() {
        Amount price = mealKitPriceFinder.findPrice(MEAL_KIT_TYPE);
        ReportBill expectedBill = reportBillFixture
            .withReportDate(DELIVERY_DATE)
            .withPrice(price)
            .build();
        int expectedBillsSize = 1;

        billService.billMealKit(CUSTOMER_ID, PRODUCT_ID, MEAL_KIT_TYPE, DELIVERY_DATE);

        List<ReportBill> actualBills = billRepository.findBillsInRange(
            YESTERDAY,
            TOMORROW
        );
        ReportBill actualBill = actualBills.get(0);
        assertEquals(expectedBillsSize, actualBills.size());
        assertEquals(expectedBill.getPrice(), actualBill.getPrice());
        assertEquals(expectedBill.getWeekNumber(), actualBill.getWeekNumber());
    }
}

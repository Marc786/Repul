package ca.ulaval.glo4003.small.repul.finance.domain.bill;

import static ca.ulaval.glo4003.constant.Constants.Validator.Bill.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.finance.MealKitBillFixture;
import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillDetail;
import ca.ulaval.glo4003.repul.finance.domain.bill.MealKitBill;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.ProductId;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MealKitBillTest {

    private static final String CUSTOMER_ID_VALUE = "ralol01";
    private static final String PRODUCT_ID_VALUE = "123123";
    private static final double PRICE_VALUE = 100.0;
    private static final String MEAL_KIT_TYPE_VALUE = "standard";
    private static final String DELIVERY_DATE = "2018-12-06";
    private static final LocalDate DELIVERY_LOCAL_DATE = LocalDate.parse(DELIVERY_DATE);
    private static final BuyerId CUSTOMER_ID = new BuyerId(CUSTOMER_ID_VALUE);
    private static final ProductId PRODUCT_ID = new ProductId(PRODUCT_ID_VALUE);
    private static final Amount PRICE = new Amount(PRICE_VALUE);
    private static final MealKitType MEAL_KIT_TYPE = MealKitType.fromString(
        MEAL_KIT_TYPE_VALUE
    );
    private static final Map<String, String> PRODUCT_DETAIL_MAP = Map.of(
        DETAIL_CUSTOMER_ID_FIELD,
        CUSTOMER_ID_VALUE,
        DETAIL_PRODUCT_ID_FIELD,
        PRODUCT_ID_VALUE,
        DETAIL_MEAL_KIT_TYPE_FIELD,
        MEAL_KIT_TYPE_VALUE,
        DETAIL_DELIVERY_DATE_FIELD,
        DELIVERY_DATE
    );
    private final MealKitBillFixture mealKitBillFixture = new MealKitBillFixture();

    private MealKitBill mealKitBill;

    @BeforeEach
    void setup() {
        this.mealKitBill =
            mealKitBillFixture
                .withCustomerId(CUSTOMER_ID)
                .withProductId(PRODUCT_ID)
                .withPrice(PRICE)
                .withMealKitType(MEAL_KIT_TYPE)
                .withDeliveryDate(DELIVERY_LOCAL_DATE)
                .build();
    }

    @Test
    void getProductDetail_returnsProductDetail() {
        BillDetail expectedBillDetail = new BillDetail(PRODUCT_DETAIL_MAP);

        BillDetail actualBillDetail = mealKitBill.getDetail();

        assertEquals(expectedBillDetail, actualBillDetail);
    }

    @Test
    void getReportDate_returnsDeliveryDate() {
        LocalDate actualDeliveryDate = mealKitBill.getEffectiveDate();

        assertEquals(DELIVERY_LOCAL_DATE, actualDeliveryDate);
    }
}

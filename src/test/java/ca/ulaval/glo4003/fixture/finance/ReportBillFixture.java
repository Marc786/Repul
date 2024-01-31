package ca.ulaval.glo4003.fixture.finance;

import static ca.ulaval.glo4003.constant.Constants.Validator.Bill.*;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import java.time.LocalDate;
import java.util.Map;

public class ReportBillFixture {

    private final BillId billId = new BillId();
    private Amount amount = new Amount(100.0);
    private LocalDate reportDate = LocalDate.parse("2023-10-10");
    private final ProductDetail details = new ProductDetail(
        Map.of(
            DETAIL_CUSTOMER_ID_FIELD,
            "customerId",
            DETAIL_PRODUCT_ID_FIELD,
            "productId",
            DETAIL_MEAL_KIT_TYPE_FIELD,
            "STANDARD",
            DETAIL_DELIVERY_DATE_FIELD,
            "2023-10-10"
        )
    );

    public ReportBill build() {
        return new ReportBill(billId, amount, reportDate, details);
    }

    public ReportBillFixture withPrice(Amount amount) {
        this.amount = amount;
        return this;
    }

    public ReportBillFixture withReportDate(LocalDate date) {
        this.reportDate = date;
        return this;
    }
}

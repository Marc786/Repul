package ca.ulaval.glo4003.small.repul.finance.domain.report.weekly_summaries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklyProductsDetail;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummary;
import java.util.List;
import org.junit.jupiter.api.Test;

class WeeklySummaryTest {

    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private final ReportBill bill = reportBillFixture.build();
    private final ReportBill anotherBill = reportBillFixture.build();
    private final int WEEK_NUMBER = 1;

    @Test
    void twoBills_getWeekNumber_returnsWeekNumber() {
        WeeklySummary weeklySummary = new WeeklySummary(
            WEEK_NUMBER,
            List.of(bill, anotherBill)
        );

        int actualWeekNumber = weeklySummary.getWeekNumber();

        assertEquals(WEEK_NUMBER, actualWeekNumber);
    }

    @Test
    void twoBills_getWeeklyTotalAmount_returnsWeeklyTotalAmount() {
        WeeklySummary weeklySummary = new WeeklySummary(
            WEEK_NUMBER,
            List.of(bill, anotherBill)
        );
        Amount expectedWeeklyTotalAmount = bill.getPrice().add(anotherBill.getPrice());

        Amount actualWeeklyTotalAmount = weeklySummary.getWeeklyTotalAmount();

        assertEquals(expectedWeeklyTotalAmount, actualWeeklyTotalAmount);
    }

    @Test
    void twoBills_getWeeklyProductsDetail_returnsWeeklyProductsDetail() {
        WeeklySummary weeklySummary = new WeeklySummary(
            WEEK_NUMBER,
            List.of(bill, anotherBill)
        );
        List<ProductDetail> expectedWeeklyProductsDetail = new WeeklyProductsDetail(
            List.of(bill, anotherBill)
        )
            .getProductsDetail();

        List<ProductDetail> actualWeeklyProductsDetail =
            weeklySummary.getWeeklyProductsDetail();

        assertEquals(expectedWeeklyProductsDetail, actualWeeklyProductsDetail);
    }

    @Test
    void noBills_getWeeklyTotalAmount_returnsAmountWithZero() {
        WeeklySummary weeklySummary = new WeeklySummary(WEEK_NUMBER, List.of());
        Amount expectedWeeklyTotalAmount = new Amount(0);

        Amount actualAmount = weeklySummary.getWeeklyTotalAmount();

        assertEquals(expectedWeeklyTotalAmount, actualAmount);
    }

    @Test
    void noBills_getWeeklyProductsDetail_returnsEmptyList() {
        WeeklySummary weeklySummary = new WeeklySummary(WEEK_NUMBER, List.of());
        List<ProductDetail> expectedWeeklyProductsDetail = List.of();

        List<ProductDetail> actualWeeklyProductsDetail =
            weeklySummary.getWeeklyProductsDetail();

        assertEquals(expectedWeeklyProductsDetail, actualWeeklyProductsDetail);
    }
}

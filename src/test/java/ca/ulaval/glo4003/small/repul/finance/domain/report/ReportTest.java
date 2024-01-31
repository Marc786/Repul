package ca.ulaval.glo4003.small.repul.finance.domain.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.fixture.semester.SemesterFixture;
import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.Report;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.ProductByPrice;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummary;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReportTest {

    private static final Amount DIFFERENT_PRICE = new Amount(120.0);
    private static final int WEEK_NUMBER_ONE = 1;
    private static final LocalDate WEEK_ONE_PAYMENT_DATE = LocalDate.of(2023, 1, 2);
    private static final int WEEK_NUMBER_TWO = 2;
    private static final LocalDate WEEK_TWO_PAYMENT_DATE = LocalDate.of(2023, 1, 9);

    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private final SemesterFixture semesterFixture = new SemesterFixture();
    private final Semester semester = semesterFixture
        .withStartDate(WEEK_ONE_PAYMENT_DATE)
        .withEndDate(WEEK_TWO_PAYMENT_DATE)
        .build();
    private final ReportBill bill = reportBillFixture
        .withReportDate(WEEK_ONE_PAYMENT_DATE)
        .build();
    private final ReportBill sameWeekBill = reportBillFixture
        .withReportDate(WEEK_ONE_PAYMENT_DATE)
        .build();

    private final ReportBill sameWeekBillDifferentPrice = reportBillFixture
        .withPrice(DIFFERENT_PRICE)
        .withReportDate(WEEK_ONE_PAYMENT_DATE)
        .build();
    private final ReportBill otherWeekBill = reportBillFixture
        .withReportDate(WEEK_TWO_PAYMENT_DATE)
        .build();

    @Test
    void threeBills_getYearlyTotalAmount_returnsSemesterTotalAmount() {
        Report report = new Report(
            List.of(bill, sameWeekBill, sameWeekBillDifferentPrice),
            semester
        );
        Amount expectedTotalAmount = bill
            .getPrice()
            .add(sameWeekBill.getPrice())
            .add(sameWeekBillDifferentPrice.getPrice());

        Amount actualTotalAmount = report.getSemesterTotalAmount();

        assertEquals(expectedTotalAmount, actualTotalAmount);
    }

    @Test
    void twoBillsWithDifferentPrices_getProductsByPrice_returnsTwoEntriesOfProductByPrice() {
        Report report = new Report(List.of(bill, sameWeekBillDifferentPrice), semester);
        List<ProductByPrice> expectedProductsByPrice = List.of(
            new ProductByPrice(bill.getPrice(), 1),
            new ProductByPrice(sameWeekBillDifferentPrice.getPrice(), 1)
        );

        List<ProductByPrice> actualProductsByPrice = report.getSemesterProductsByPrice();

        assertEquals(expectedProductsByPrice, actualProductsByPrice);
    }

    @Test
    void fourBillsOnTwoWeeks_getWeeklySummaries_returnsWeeklySummaries() {
        Report report = new Report(
            List.of(bill, sameWeekBill, sameWeekBillDifferentPrice, otherWeekBill),
            semester
        );
        List<WeeklySummary> expectedWeeklySummaries = List.of(
            new WeeklySummary(
                WEEK_NUMBER_ONE,
                List.of(bill, sameWeekBill, sameWeekBillDifferentPrice)
            ),
            new WeeklySummary(WEEK_NUMBER_TWO, List.of(otherWeekBill))
        );

        List<WeeklySummary> actualWeeklySummaries = report.getWeeklySummaries();

        assertEquals(expectedWeeklySummaries, actualWeeklySummaries);
    }
}

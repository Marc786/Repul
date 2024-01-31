package ca.ulaval.glo4003.small.repul.finance.domain.report.weekly_summaries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.fixture.semester.SemesterFixture;
import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummaries;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummary;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class WeeklySummariesTest {

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
    private final ReportBill anotherBill = reportBillFixture.build();
    private final ReportBill billWithOtherWeekReportDate = reportBillFixture
        .withReportDate(WEEK_TWO_PAYMENT_DATE)
        .build();

    @Test
    void noBill_getWeeklySummaries_returnsEmptySummaries() {
        WeeklySummaries weeklySummaries = new WeeklySummaries(List.of(), semester);
        List<WeeklySummary> expectedWeeklySummaries = List.of(
            new WeeklySummary(WEEK_NUMBER_ONE, List.of()),
            new WeeklySummary(WEEK_NUMBER_TWO, List.of())
        );

        List<WeeklySummary> actualWeeklySummaries = weeklySummaries.getWeeklySummaries();

        assertEquals(expectedWeeklySummaries, actualWeeklySummaries);
    }

    @Test
    void twoBillsFromTheSameWeek_getWeeklySummaries_returnsOnePopulatedWeeklySummary() {
        WeeklySummaries weeklySummaries = new WeeklySummaries(
            List.of(bill, anotherBill),
            semester
        );
        List<WeeklySummary> expectedWeeklySummaries = List.of(
            new WeeklySummary(WEEK_NUMBER_ONE, List.of(bill, anotherBill)),
            new WeeklySummary(WEEK_NUMBER_TWO, List.of())
        );

        List<WeeklySummary> actualWeeklySummaries = weeklySummaries.getWeeklySummaries();

        assertEquals(expectedWeeklySummaries, actualWeeklySummaries);
    }

    @Test
    void twoBillsFromDifferentWeeks_getWeeklySummaries_returnsTwoWeeklySummaries() {
        WeeklySummaries weeklySummaries = new WeeklySummaries(
            List.of(bill, billWithOtherWeekReportDate),
            semester
        );
        List<WeeklySummary> expectedWeeklySummaries = List.of(
            new WeeklySummary(WEEK_NUMBER_ONE, List.of(bill)),
            new WeeklySummary(WEEK_NUMBER_TWO, List.of(billWithOtherWeekReportDate))
        );

        List<WeeklySummary> actualWeeklySummaries = weeklySummaries.getWeeklySummaries();

        assertEquals(expectedWeeklySummaries, actualWeeklySummaries);
    }
}

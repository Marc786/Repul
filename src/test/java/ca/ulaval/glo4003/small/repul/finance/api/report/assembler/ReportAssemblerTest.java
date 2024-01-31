package ca.ulaval.glo4003.small.repul.finance.api.report.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.fixture.response.ReportResponseFixture;
import ca.ulaval.glo4003.fixture.semester.SemesterFixture;
import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.repul.finance.api.report.assembler.ReportAssembler;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.*;
import ca.ulaval.glo4003.repul.finance.domain.report.Report;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportAssemblerTest {

    private static final LocalDate SEMESTER_START_DATE = LocalDate.of(2023, 11, 6);
    private static final LocalDate SEMESTER_END_DATE = LocalDate.of(2023, 11, 10);
    private static final int WEEK_NUMBER = 45;

    private final ReportResponseFixture reportResponseFixture =
        new ReportResponseFixture();
    private final ReportBillFixture mealKitBillFixture = new ReportBillFixture();
    private final SemesterFixture semesterFixture = new SemesterFixture();
    private final Semester SEMESTER = semesterFixture
        .withCode(new SemesterCode("H23"))
        .withStartDate(SEMESTER_START_DATE)
        .withEndDate(SEMESTER_END_DATE)
        .build();
    private final ReportBill billInWeek = mealKitBillFixture
        .withReportDate(SEMESTER_START_DATE.plusDays(1))
        .build();
    private final WeeklyTotalAmountResponse WEEKLY_TOTAL_AMOUNT_RESPONSE =
        new WeeklyTotalAmountResponse(billInWeek.getPrice().value());
    private final List<ProductDetailResponse> WEEKLY_PRODUCT_DETAILS_RESPONSES = List.of(
        new ProductDetailResponse(billInWeek.getDetail().getAsMap())
    );
    private final List<WeeklySummaryResponse> WEEKLY_SUMMARIES_RESPONSES = List.of(
        new WeeklySummaryResponse(
            WEEK_NUMBER,
            WEEKLY_TOTAL_AMOUNT_RESPONSE,
            WEEKLY_PRODUCT_DETAILS_RESPONSES
        )
    );
    private final List<ProductByPriceResponse> PRODUCTS_BY_PRICE_RESPONSES = List.of(
        new ProductByPriceResponse(billInWeek.getPrice().value(), 1)
    );
    private final Report REPORT = new Report(List.of(billInWeek), SEMESTER);
    private ReportAssembler reportAssembler;

    @BeforeEach
    void setup() {
        reportAssembler = new ReportAssembler();
    }

    @Test
    void toResponse_returnsExpectedResponse() {
        ReportResponse expectedReportResponse = reportResponseFixture
            .withSemesterCode(SEMESTER.code().toString())
            .withTotalAmount(billInWeek.getPrice().value())
            .withProductsByPrice(PRODUCTS_BY_PRICE_RESPONSES)
            .withWeeklySummaries(WEEKLY_SUMMARIES_RESPONSES)
            .build();

        ReportResponse actualReportResponse = reportAssembler.toResponse(REPORT);

        assertEquals(expectedReportResponse, actualReportResponse);
    }
}

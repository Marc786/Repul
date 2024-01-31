package ca.ulaval.glo4003.medium.finance.application.report;

import ca.ulaval.glo4003.constant.Constants;
import ca.ulaval.glo4003.fixture.finance.MealKitBillFixture;
import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.semester.exception.SemesterCodeNotFoundException;
import ca.ulaval.glo4003.lib.semester.registry.SemesterRegistry;
import ca.ulaval.glo4003.lib.semester.registry.finder.ReportSemesterFinder;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.application.report.ReportService;
import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.domain.report.Report;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.ProductByPrice;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummary;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import ca.ulaval.glo4003.repul.finance.infra.InMemoryBillRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup.resetClock;
import static ca.ulaval.glo4003.constant.Constants.ClockSetup.setClock;
import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest {

    private static final LocalDate IN_WINTER_SEMESTER_DATE = LocalDate.parse(
        "2025-01-15"
    );
    private static final LocalDate AFTER_WINTER_SEMESTER_DATE = LocalDate.parse(
        "2025-04-30"
    );
    private static final SemesterCode WINTER_SEMESTER_CODE = new SemesterCode("H25");
    private static final SemesterCode INVALID_SEMESTER_CODE = new SemesterCode("H10");
    private static final int WEEK_NUMBER_ONE = 1;
    private static final LocalDate WEEK_ONE_START_DATE = LocalDate.of(2025, 1, 13);
    private static final LocalDate DATE_IN_WEEK_ONE = WEEK_ONE_START_DATE.plusDays(1);
    private static final int WEEK_NUMBER_TWO = 2;
    private static final LocalDate WEEK_TWO_END_DATE = LocalDate.of(2025, 1, 26);
    private static final LocalDate DATE_IN_WEEK_TWO = WEEK_TWO_END_DATE.minusDays(1);
    private static final Amount DIFFERENT_PRICE = new Amount(120.0);

    private final MealKitBillFixture mealKitBillFixture = new MealKitBillFixture();
    private final Bill bill = mealKitBillFixture
        .withBillId(new BillId())
        .withDeliveryDate(DATE_IN_WEEK_ONE.plusDays(1))
        .build();
    private final Bill otherWeekBill = mealKitBillFixture
        .withBillId(new BillId())
        .withDeliveryDate(DATE_IN_WEEK_TWO)
        .build();
    private final Bill sameWeekBillDifferentPrice = mealKitBillFixture
        .withBillId(new BillId())
        .withPrice(DIFFERENT_PRICE)
        .withDeliveryDate(DATE_IN_WEEK_ONE)
        .build();

    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private final ReportBill reportBill = reportBillFixture
        .withReportDate(DATE_IN_WEEK_ONE.plusDays(1))
        .build();
    private final ReportBill otherWeekReportBill = reportBillFixture
        .withReportDate(DATE_IN_WEEK_TWO)
        .build();
    private final ReportBill sameWeekReportBillDifferentPrice = reportBillFixture
        .withPrice(DIFFERENT_PRICE)
        .withReportDate(DATE_IN_WEEK_ONE)
        .build();

    private ReportSemesterFinder reportSemesterFinder;
    private BillRepository billRepository;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        billRepository = new InMemoryBillRepository();
        reportSemesterFinder = new SemesterRegistry(Constants.Path.JSON_SEMESTERS);
        reportService = new ReportService(billRepository, reportSemesterFinder);
    }

    @AfterEach
    void tearDown() {
        resetClock();
    }

    @Test
    void validSemesterCode_generateReport_returnsReportFromSemester() {
        setClock(IN_WINTER_SEMESTER_DATE);
        Report report = reportService.generateReport(
            Optional.of(WINTER_SEMESTER_CODE.toString())
        );

        assertEquals(WINTER_SEMESTER_CODE, report.getSemesterCode());
    }

    @Test
    void invalidSemesterCode_generateReport_throwsSemesterCodeNotFoundException() {
        assertThrows(
            SemesterCodeNotFoundException.class,
            () ->
                reportService.generateReport(
                    Optional.of(INVALID_SEMESTER_CODE.toString())
                )
        );
    }

    @Test
    void inBetweenSemesterDate_generateReport_returnsReportFromPreviousSemester() {
        setClock(AFTER_WINTER_SEMESTER_DATE);

        Report report = reportService.generateReport(Optional.empty());

        assertEquals(WINTER_SEMESTER_CODE, report.getSemesterCode());
    }

    @Test
    void withoutBillsInSemester_generateReport_returnsEmptyReport() {
        setClock(IN_WINTER_SEMESTER_DATE);
        Amount expectedTotalAmount = new Amount(0.0);
        List<WeeklySummary> expectedWeeklySummaries =
            getExpectedEmptyWinterWeeklySummaries();

        Report report = reportService.generateReport(Optional.empty());

        Amount actualTotalAmount = report.getSemesterTotalAmount();
        List<ProductByPrice> actualProductsByPrice = report.getSemesterProductsByPrice();
        List<WeeklySummary> actualWeeklySummaries = report.getWeeklySummaries();
        assertEquals(expectedTotalAmount, actualTotalAmount);
        assertTrue(actualProductsByPrice.isEmpty());
        assertEquals(expectedWeeklySummaries, actualWeeklySummaries);
    }

    @Test
    void twoBillsInSemester_generateReport_returnsReportWithTotalAmount() {
        setClock(IN_WINTER_SEMESTER_DATE);
        double expectedTotal = bill.getPrice().value() + otherWeekBill.getPrice().value();
        billRepository.save(bill);
        billRepository.save(otherWeekBill);

        Report report = reportService.generateReport(Optional.empty());

        double actualTotal = report.getSemesterTotalAmount().value();
        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    void threeBillsInSemester_generateReport_returnsReportWithProductsByPrice() {
        setClock(IN_WINTER_SEMESTER_DATE);
        billRepository.save(bill);
        billRepository.save(sameWeekBillDifferentPrice);
        billRepository.save(otherWeekBill);
        List<ProductByPrice> expectedProductsByPrice = List.of(
            new ProductByPrice(bill.getPrice(), 2),
            new ProductByPrice(sameWeekBillDifferentPrice.getPrice(), 1)
        );

        Report report = reportService.generateReport(Optional.empty());

        List<ProductByPrice> actualProductsByPrice = report.getSemesterProductsByPrice();
        assertEquals(expectedProductsByPrice, actualProductsByPrice);
    }

    @Test
    void threeBillsInSemester_generateReport_returnsWeeklySummariesWithExpectedTotal() {
        setClock(IN_WINTER_SEMESTER_DATE);
        billRepository.save(bill);
        billRepository.save(sameWeekBillDifferentPrice);
        billRepository.save(otherWeekBill);
        List<WeeklySummary> expectedWeeklySummaries = List.of(
            new WeeklySummary(
                WEEK_NUMBER_ONE,
                List.of(reportBill, sameWeekReportBillDifferentPrice)
            ),
            new WeeklySummary(WEEK_NUMBER_TWO, List.of(otherWeekReportBill))
        );

        Report report = reportService.generateReport(Optional.empty());

        List<WeeklySummary> actualWeeklySummaries = report.getWeeklySummaries();
        assertEquals(
            expectedWeeklySummaries.get(0).getWeeklyTotalAmount(),
            actualWeeklySummaries.get(0).getWeeklyTotalAmount()
        );
        assertEquals(
            expectedWeeklySummaries.get(1).getWeeklyTotalAmount(),
            actualWeeklySummaries.get(1).getWeeklyTotalAmount()
        );
    }

    private List<WeeklySummary> getExpectedEmptyWinterWeeklySummaries() {
        Semester semester = reportSemesterFinder.findCurrentOrPreviousSemester(
            IN_WINTER_SEMESTER_DATE
        );

        return semester
            .getWeekNumbers()
            .stream()
            .map(weekNumber -> new WeeklySummary(weekNumber, List.of()))
            .toList();
    }
}

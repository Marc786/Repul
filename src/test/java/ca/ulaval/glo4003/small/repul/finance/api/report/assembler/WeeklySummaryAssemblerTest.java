package ca.ulaval.glo4003.small.repul.finance.api.report.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.repul.finance.api.report.assembler.WeeklySummaryAssembler;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ProductDetailResponse;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.WeeklySummaryResponse;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.WeeklyTotalAmountResponse;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummary;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeeklySummaryAssemblerTest {

    private static final int WEEK_NUMBER = 1;

    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private final ReportBill bill = reportBillFixture.build();
    private final ReportBill anotherBill = reportBillFixture.build();
    private final ProductDetailResponse billProductDetailResponse =
        new ProductDetailResponse(bill.getDetail().getAsMap());
    private final ProductDetailResponse otherBillProductDetailResponse =
        new ProductDetailResponse(anotherBill.getDetail().getAsMap());
    private final WeeklyTotalAmountResponse weeklyTotalAmountResponse =
        new WeeklyTotalAmountResponse(
            bill.getPrice().value() + anotherBill.getPrice().value()
        );

    private final WeeklySummary WEEKLY_SUMMARY = new WeeklySummary(
        WEEK_NUMBER,
        List.of(bill, anotherBill)
    );
    private WeeklySummaryAssembler weeklySummaryAssembler;

    @BeforeEach
    void setup() {
        this.weeklySummaryAssembler = new WeeklySummaryAssembler();
    }

    @Test
    void emptyList_toResponse_returnsEmptyList() {
        List<WeeklySummaryResponse> actualWeeklySummaryResponses = List.of();

        List<WeeklySummaryResponse> expectedWeeklySummaryResponses =
            weeklySummaryAssembler.toResponse(List.of());

        assertEquals(expectedWeeklySummaryResponses, actualWeeklySummaryResponses);
    }

    @Test
    void twoBillsSameWeek_toResponse_returnsExpectedWeeklySummaryResponses() {
        List<WeeklySummaryResponse> expectedWeeklySummaryResponses = List.of(
            new WeeklySummaryResponse(
                WEEK_NUMBER,
                weeklyTotalAmountResponse,
                List.of(billProductDetailResponse, otherBillProductDetailResponse)
            )
        );

        List<WeeklySummaryResponse> actualWeeklySummaryResponses =
            weeklySummaryAssembler.toResponse(List.of(WEEKLY_SUMMARY));

        assertEquals(expectedWeeklySummaryResponses, actualWeeklySummaryResponses);
    }
}

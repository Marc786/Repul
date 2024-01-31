package ca.ulaval.glo4003.small.repul.finance.api.report.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.api.report.assembler.WeeklyTotalAmountAssembler;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.WeeklyTotalAmountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeeklyTotalAmountAssemblerTest {

    private final Amount WEEKLY_TOTAL_AMOUNT = new Amount(1200.0);
    private WeeklyTotalAmountAssembler weeklyTotalAmountAssembler;

    @BeforeEach
    void setup() {
        this.weeklyTotalAmountAssembler = new WeeklyTotalAmountAssembler();
    }

    @Test
    void toResponse_returnsExpectedWeeklyTotalAmountResponse() {
        WeeklyTotalAmountResponse expectedWeeklyTotalAmountResponse =
            new WeeklyTotalAmountResponse(WEEKLY_TOTAL_AMOUNT.value());

        WeeklyTotalAmountResponse actualWeeklyTotalAmountResponse =
            weeklyTotalAmountAssembler.toResponse(WEEKLY_TOTAL_AMOUNT);

        assertEquals(expectedWeeklyTotalAmountResponse, actualWeeklyTotalAmountResponse);
    }
}

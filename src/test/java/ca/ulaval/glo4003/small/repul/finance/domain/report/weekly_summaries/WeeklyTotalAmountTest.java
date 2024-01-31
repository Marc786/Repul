package ca.ulaval.glo4003.small.repul.finance.domain.report.weekly_summaries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklyTotalAmount;
import java.util.List;
import org.junit.jupiter.api.Test;

class WeeklyTotalAmountTest {

    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private final ReportBill bill = reportBillFixture.build();
    private final ReportBill anotherBill = reportBillFixture.build();

    @Test
    void twoBills_getTotalAmount_returnsAmountWithCorrectSum() {
        WeeklyTotalAmount weeklyTotalAmount = new WeeklyTotalAmount(
            List.of(bill, anotherBill)
        );
        Amount expectedAmount = bill.getPrice().add(anotherBill.getPrice());

        Amount actualAmount = weeklyTotalAmount.getTotalAmount();

        assertEquals(expectedAmount, actualAmount);
    }

    @Test
    void noBills_getTotalAmount_returnsAmountWithZero() {
        WeeklyTotalAmount weeklyTotalAmount = new WeeklyTotalAmount(List.of());
        Amount expectedAmount = new Amount(0);

        Amount actualAmount = weeklyTotalAmount.getTotalAmount();

        assertEquals(expectedAmount, actualAmount);
    }
}

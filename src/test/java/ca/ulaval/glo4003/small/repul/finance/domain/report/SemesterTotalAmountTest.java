package ca.ulaval.glo4003.small.repul.finance.domain.report;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.SemesterTotalAmount;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SemesterTotalAmountTest {

    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private final ReportBill bill = reportBillFixture.build();
    private final ReportBill anotherBill = reportBillFixture.build();

    @Test
    void twoBills_getTotalAmount_returnsSumOfBillAmounts() {
        SemesterTotalAmount semesterTotalAmount = new SemesterTotalAmount(
            List.of(bill, anotherBill)
        );
        Amount expectedTotalAmount = bill.getPrice().add(anotherBill.getPrice());

        Amount actualTotalAmount = semesterTotalAmount.getTotalAmount();

        Assertions.assertEquals(expectedTotalAmount, actualTotalAmount);
    }

    @Test
    void noBills_getTotalAmount_returnsZero() {
        SemesterTotalAmount semesterTotalAmount = new SemesterTotalAmount(List.of());
        Amount expectedTotalAmount = new Amount(0);

        Amount actualTotalAmount = semesterTotalAmount.getTotalAmount();

        Assertions.assertEquals(expectedTotalAmount, actualTotalAmount);
    }
}

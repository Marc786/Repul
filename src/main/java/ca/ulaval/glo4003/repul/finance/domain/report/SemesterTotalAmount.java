package ca.ulaval.glo4003.repul.finance.domain.report;

import ca.ulaval.glo4003.lib.value_object.Amount;
import java.util.List;

public class SemesterTotalAmount {

    private Amount totalAmount;

    public SemesterTotalAmount(List<ReportBill> bills) {
        calculateTotalAmount(bills);
    }

    private void calculateTotalAmount(List<ReportBill> bills) {
        totalAmount = new Amount(0);
        bills.forEach(bill -> totalAmount = totalAmount.add(bill.getPrice()));
    }

    public Amount getTotalAmount() {
        return totalAmount;
    }
}

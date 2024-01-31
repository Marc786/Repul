package ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import java.util.List;
import java.util.Objects;

public class WeeklyTotalAmount {

    private Amount totalAmount;

    public WeeklyTotalAmount(List<ReportBill> bills) {
        calculateTotalAmount(bills);
    }

    public Amount getTotalAmount() {
        return totalAmount;
    }

    private void calculateTotalAmount(List<ReportBill> bills) {
        totalAmount = new Amount(0);
        bills.forEach(bill -> totalAmount = totalAmount.add(bill.getPrice()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (WeeklyTotalAmount) obj;
        return Objects.equals(this.totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalAmount);
    }
}

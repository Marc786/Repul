package ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import java.util.List;

public class WeeklySummary {

    private final int weekNumber;
    private final WeeklyTotalAmount weeklyTotalAmount;
    private final WeeklyProductsDetail weeklyProductsDetail;

    public WeeklySummary(int weekNumber, List<ReportBill> bills) {
        this.weekNumber = weekNumber;
        this.weeklyTotalAmount = new WeeklyTotalAmount(bills);
        this.weeklyProductsDetail = new WeeklyProductsDetail(bills);
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public Amount getWeeklyTotalAmount() {
        return weeklyTotalAmount.getTotalAmount();
    }

    public List<ProductDetail> getWeeklyProductsDetail() {
        return weeklyProductsDetail.getProductsDetail();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WeeklySummary that)) {
            return false;
        }
        return (
            this.weekNumber == that.weekNumber &&
            this.weeklyTotalAmount.equals(that.weeklyTotalAmount) &&
            this.weeklyProductsDetail.equals(that.weeklyProductsDetail)
        );
    }

    @Override
    public int hashCode() {
        return weekNumber;
    }
}

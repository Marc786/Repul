package ca.ulaval.glo4003.repul.finance.domain.report;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.ProductByPrice;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.SemesterProductsByPrice;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummaries;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummary;
import java.util.List;

public class Report {

    private final SemesterTotalAmount semesterTotalAmount;
    private final SemesterProductsByPrice semesterProductsByPrice;
    private final WeeklySummaries weeklySummaries;
    private final SemesterCode semesterCode;

    public Report(List<ReportBill> bills, Semester semester) {
        this.semesterTotalAmount = new SemesterTotalAmount(bills);
        this.semesterProductsByPrice = new SemesterProductsByPrice(bills);
        this.weeklySummaries = new WeeklySummaries(bills, semester);
        this.semesterCode = semester.code();
    }

    public SemesterCode getSemesterCode() {
        return semesterCode;
    }

    public Amount getSemesterTotalAmount() {
        return semesterTotalAmount.getTotalAmount();
    }

    public List<ProductByPrice> getSemesterProductsByPrice() {
        return semesterProductsByPrice.getProductsByPrice();
    }

    public List<WeeklySummary> getWeeklySummaries() {
        return weeklySummaries.getWeeklySummaries();
    }
}

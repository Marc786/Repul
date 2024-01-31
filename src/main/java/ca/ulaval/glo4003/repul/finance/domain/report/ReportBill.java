package ca.ulaval.glo4003.repul.finance.domain.report;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class ReportBill implements Reportable {

    private final BillId id;
    private final Amount price;
    private final LocalDate reportDate;
    private final ProductDetail details;

    public ReportBill(
        BillId id,
        Amount price,
        LocalDate reportDate,
        ProductDetail details
    ) {
        this.id = id;
        this.price = price;
        this.reportDate = reportDate;
        this.details = details;
    }

    public BillId getId() {
        return id;
    }

    public Amount getPrice() {
        return price;
    }

    @Override
    public ProductDetail getDetail() {
        return details;
    }

    @Override
    public int getWeekNumber() {
        return reportDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }
}

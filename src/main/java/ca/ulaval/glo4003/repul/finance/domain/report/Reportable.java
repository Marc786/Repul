package ca.ulaval.glo4003.repul.finance.domain.report;

import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;

public interface Reportable {
    ProductDetail getDetail();

    int getWeekNumber();
}

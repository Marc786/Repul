package ca.ulaval.glo4003.repul.finance.api.report.assembler;

import ca.ulaval.glo4003.repul.finance.api.report.dto.response.WeeklySummaryResponse;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklySummary;
import java.util.List;

public class WeeklySummaryAssembler {

    private final WeeklyTotalAmountAssembler weeklyTotalAmountAssembler;
    private final WeeklyProductDetailAssembler weeklyProductDetailAssembler;

    public WeeklySummaryAssembler() {
        this.weeklyTotalAmountAssembler = new WeeklyTotalAmountAssembler();
        this.weeklyProductDetailAssembler = new WeeklyProductDetailAssembler();
    }

    public List<WeeklySummaryResponse> toResponse(List<WeeklySummary> weeklySummaries) {
        return weeklySummaries.stream().map(this::toResponse).toList();
    }

    private WeeklySummaryResponse toResponse(WeeklySummary weeklySummary) {
        return new WeeklySummaryResponse(
            weeklySummary.getWeekNumber(),
            weeklyTotalAmountAssembler.toResponse(weeklySummary.getWeeklyTotalAmount()),
            weeklyProductDetailAssembler.toResponse(
                weeklySummary.getWeeklyProductsDetail()
            )
        );
    }
}

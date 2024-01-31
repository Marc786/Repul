package ca.ulaval.glo4003.repul.finance.api.report.dto.response;

import java.util.List;

public record WeeklySummaryResponse(
    int weekNumber,
    WeeklyTotalAmountResponse weeklyTotalAmount,
    List<ProductDetailResponse> weeklyProductsDetail
) {}

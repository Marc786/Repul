package ca.ulaval.glo4003.repul.finance.api.report.dto.response;

import java.util.List;

public record ReportResponse(
    String semesterCode,
    double totalAmount,
    List<ProductByPriceResponse> productsByPrice,
    List<WeeklySummaryResponse> weeklySummaries
) {}

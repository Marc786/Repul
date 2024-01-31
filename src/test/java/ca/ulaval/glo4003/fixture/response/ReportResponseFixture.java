package ca.ulaval.glo4003.fixture.response;

import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ProductByPriceResponse;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ReportResponse;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.WeeklySummaryResponse;
import java.util.List;

public class ReportResponseFixture {

    private String semesterCode = "A23";
    private double totalAmount = 0;
    private List<ProductByPriceResponse> productsByPrice = List.of();
    private List<WeeklySummaryResponse> weeklySummaries = List.of();

    public ReportResponse build() {
        return new ReportResponse(
            semesterCode,
            totalAmount,
            productsByPrice,
            weeklySummaries
        );
    }

    public ReportResponseFixture withSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
        return this;
    }

    public ReportResponseFixture withTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public ReportResponseFixture withProductsByPrice(
        List<ProductByPriceResponse> productsByPrice
    ) {
        this.productsByPrice = productsByPrice;
        return this;
    }

    public ReportResponseFixture withWeeklySummaries(
        List<WeeklySummaryResponse> weeklySummaries
    ) {
        this.weeklySummaries = weeklySummaries;
        return this;
    }
}

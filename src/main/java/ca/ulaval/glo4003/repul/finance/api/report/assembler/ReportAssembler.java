package ca.ulaval.glo4003.repul.finance.api.report.assembler;

import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ReportResponse;
import ca.ulaval.glo4003.repul.finance.domain.report.Report;

public class ReportAssembler {

    private final ProductByPriceAssembler productByPriceAssembler;
    private final WeeklySummaryAssembler weeklySummaryAssembler;

    public ReportAssembler() {
        this.productByPriceAssembler = new ProductByPriceAssembler();
        this.weeklySummaryAssembler = new WeeklySummaryAssembler();
    }

    public ReportResponse toResponse(Report report) {
        return new ReportResponse(
            report.getSemesterCode().toString(),
            report.getSemesterTotalAmount().value(),
            productByPriceAssembler.toResponse(report.getSemesterProductsByPrice()),
            weeklySummaryAssembler.toResponse(report.getWeeklySummaries())
        );
    }
}

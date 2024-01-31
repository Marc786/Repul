package ca.ulaval.glo4003.repul.finance.api.config;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.lib.semester.registry.SemesterRegistry;
import ca.ulaval.glo4003.lib.semester.registry.finder.ReportSemesterFinder;
import ca.ulaval.glo4003.repul.finance.api.report.ReportResource;
import ca.ulaval.glo4003.repul.finance.api.report.assembler.ReportAssembler;
import ca.ulaval.glo4003.repul.finance.application.report.ReportService;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;

public class ReportResourceFactory {

    private final ReportAssembler reportAssembler;
    private final ReportService reportService;

    public ReportResourceFactory() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        BillRepository billRepository = serviceLocator.getService(BillRepository.class);
        ReportSemesterFinder reportSemesterFinder = serviceLocator.getService(
            SemesterRegistry.class
        );

        this.reportAssembler = new ReportAssembler();
        this.reportService = new ReportService(billRepository, reportSemesterFinder);
    }

    public ReportResource create() {
        return new ReportResource(reportAssembler, reportService);
    }
}

package ca.ulaval.glo4003.repul.finance.application.report;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup.getClock;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.semester.registry.finder.ReportSemesterFinder;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.domain.report.Report;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ReportService {

    private final BillRepository billRepository;
    private final ReportSemesterFinder reportSemesterFinder;

    public ReportService(
        BillRepository billRepository,
        ReportSemesterFinder reportSemesterFinder
    ) {
        this.billRepository = billRepository;
        this.reportSemesterFinder = reportSemesterFinder;
    }

    public Report generateReport(Optional<String> semesterCode) {
        Semester semester;
        if (semesterCode.isPresent()) {
            SemesterCode code = new SemesterCode(semesterCode.get());
            semester = reportSemesterFinder.getSemesterByCode(code);
        } else {
            semester =
                reportSemesterFinder.findCurrentOrPreviousSemester(
                    LocalDate.now(getClock())
                );
        }

        List<ReportBill> bills = billRepository.findBillsInRange(
            semester.startDate(),
            semester.endDate()
        );

        return new Report(bills, semester);
    }
}

package ca.ulaval.glo4003.repul.finance.domain.bill;

import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import java.time.LocalDate;
import java.util.List;

public interface BillRepository {
    void save(Bill bill);

    List<ReportBill> findBillsInRange(LocalDate startDate, LocalDate endDate);
}

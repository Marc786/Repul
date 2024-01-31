package ca.ulaval.glo4003.repul.finance.infra;

import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.infra.dto.BillDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryBillRepository implements BillRepository {

    private final BillDtoAssembler billDtoAssembler = new BillDtoAssembler();
    private final List<BillDto> bills = new ArrayList<>();

    @Override
    public void save(Bill bill) {
        BillDto billDto = billDtoAssembler.toDto(bill);

        removeIfExisting(billDto);
        bills.add(billDto);
    }

    @Override
    public List<ReportBill> findBillsInRange(LocalDate startDate, LocalDate endDate) {
        List<BillDto> billsFound = bills
            .stream()
            .filter(bill ->
                LocalDate.parse(bill.reportDate()).isAfter(startDate) &&
                LocalDate.parse(bill.reportDate()).isBefore(endDate)
            )
            .toList();

        return billsFound
            .stream()
            .map(billDtoAssembler::fromDto)
            .collect(Collectors.toList());
    }

    private void removeIfExisting(BillDto bill) {
        bills.removeIf(existingBill -> existingBill.id().equals(bill.id()));
    }
}

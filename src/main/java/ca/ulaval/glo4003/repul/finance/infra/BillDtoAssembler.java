package ca.ulaval.glo4003.repul.finance.infra;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import ca.ulaval.glo4003.repul.finance.infra.dto.BillDto;
import java.time.LocalDate;

public class BillDtoAssembler {

    public BillDto toDto(Bill bill) {
        return new BillDto(
            bill.getId().toString(),
            bill.getCustomerId().toString(),
            bill.getPrice().toString(),
            bill.getEffectiveDate().toString(),
            bill.getDetail().getAsMap()
        );
    }

    public ReportBill fromDto(BillDto billDto) {
        return new ReportBill(
            new BillId(billDto.id()),
            new Amount(Double.parseDouble(billDto.price())),
            LocalDate.parse(billDto.reportDate()),
            new ProductDetail(billDto.details())
        );
    }
}

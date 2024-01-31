package ca.ulaval.glo4003.repul.finance.domain.bill;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import java.time.LocalDate;

public interface Bill {
    BillId getId();
    BuyerId getCustomerId();
    Amount getPrice();
    LocalDate getEffectiveDate();
    BillDetail getDetail();
}

package ca.ulaval.glo4003.repul.finance.domain;

import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;

public interface PaymentClient {
    void pay(Bill bill);
}

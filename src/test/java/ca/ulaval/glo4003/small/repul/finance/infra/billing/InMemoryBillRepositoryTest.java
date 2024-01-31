package ca.ulaval.glo4003.small.repul.finance.infra.billing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import ca.ulaval.glo4003.fixture.finance.MealKitBillFixture;
import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;
import ca.ulaval.glo4003.repul.finance.domain.bill.BillRepository;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BillId;
import ca.ulaval.glo4003.repul.finance.infra.InMemoryBillRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryBillRepositoryTest {

    private final MealKitBillFixture mealKitBillFixture = new MealKitBillFixture();
    private final LocalDate TODAY = LocalDate.of(2023, 10, 10);
    private final LocalDate YESTERDAY = TODAY.minusDays(1);
    private final LocalDate TOMORROW = TODAY.plusDays(1);
    private BillRepository billRepository;

    @BeforeEach
    void setup() {
        billRepository = new InMemoryBillRepository();
    }

    @Test
    void billsInRange_findBillsInRange_returnsBills() {
        Bill bill = mealKitBillFixture.withDeliveryDate(TODAY).build();
        Bill anotherBill = mealKitBillFixture
            .withBillId(new BillId())
            .withDeliveryDate(TODAY)
            .build();
        billRepository.save(bill);
        billRepository.save(anotherBill);
        List<BillId> expectedBillsId = Stream
            .of(bill, anotherBill)
            .map(Bill::getId)
            .toList();

        List<BillId> actualBillsId = billRepository
            .findBillsInRange(YESTERDAY, TOMORROW)
            .stream()
            .map(ReportBill::getId)
            .toList();

        assertNotSame(expectedBillsId, actualBillsId);
        assertEquals(expectedBillsId, actualBillsId);
    }

    @Test
    void noBillInRange_findBillsInRange_returnsEmptyList() {
        Bill bill = mealKitBillFixture.withDeliveryDate(YESTERDAY).build();
        billRepository.save(bill);
        List<Bill> expectedBills = List.of();

        List<ReportBill> actualBills = billRepository.findBillsInRange(TODAY, TOMORROW);

        assertEquals(expectedBills, actualBills);
    }
}

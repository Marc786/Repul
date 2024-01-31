package ca.ulaval.glo4003.small.repul.finance.domain.report.semester_products_by_price;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.ProductByPrice;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.SemesterProductsByPrice;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SemesterProductsByPriceTest {

    private static final Amount DIFFERENT_PRICE = new Amount(120.0);

    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private final ReportBill bill = reportBillFixture.build();
    private final ReportBill anotherBill = reportBillFixture.build();
    private final ReportBill billWithDifferentPrice = reportBillFixture
        .withPrice(DIFFERENT_PRICE)
        .build();

    @Test
    void twoBillsWithSamePrice_getProductsByPrice_returnsOneEntryOfProductByPrice() {
        SemesterProductsByPrice semesterProductsByPrice = new SemesterProductsByPrice(
            List.of(bill, anotherBill)
        );
        List<ProductByPrice> expectedProductsByPrice = List.of(
            new ProductByPrice(bill.getPrice(), 2)
        );

        List<ProductByPrice> actualProductsByPrice =
            semesterProductsByPrice.getProductsByPrice();

        Assertions.assertEquals(expectedProductsByPrice, actualProductsByPrice);
    }

    @Test
    void twoBillsWithDifferentPrices_getProductsByPrice_returnsTwoEntriesOfProductByPrice() {
        SemesterProductsByPrice semesterProductsByPrice = new SemesterProductsByPrice(
            List.of(bill, billWithDifferentPrice)
        );
        List<ProductByPrice> expectedProductsByPrice = List.of(
            new ProductByPrice(bill.getPrice(), 1),
            new ProductByPrice(billWithDifferentPrice.getPrice(), 1)
        );

        List<ProductByPrice> actualProductsByPrice =
            semesterProductsByPrice.getProductsByPrice();

        Assertions.assertEquals(expectedProductsByPrice, actualProductsByPrice);
    }

    @Test
    void noBills_getProductsByPrice_returnsEmptyList() {
        SemesterProductsByPrice semesterProductsByPrice = new SemesterProductsByPrice(
            List.of()
        );
        List<ProductByPrice> expectedProductsByPrice = List.of();

        List<ProductByPrice> actualProductsByPrice =
            semesterProductsByPrice.getProductsByPrice();

        Assertions.assertEquals(expectedProductsByPrice, actualProductsByPrice);
    }
}

package ca.ulaval.glo4003.small.repul.finance.domain.report.weekly_summaries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.fixture.finance.ReportBillFixture;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.WeeklyProductsDetail;
import java.util.List;
import org.junit.jupiter.api.Test;

class WeeklyProductsDetailTest {

    private final ReportBillFixture reportBillFixture = new ReportBillFixture();
    private final ReportBill bill = reportBillFixture.build();
    private final ReportBill anotherBill = reportBillFixture.build();

    @Test
    void twoBills_getProductsDetail_returnsTwoEntriesOfProductDetail() {
        List<ProductDetail> expectedProductsDetail = List.of(
            bill.getDetail(),
            anotherBill.getDetail()
        );
        WeeklyProductsDetail weeklyProductsDetail = new WeeklyProductsDetail(
            List.of(bill, anotherBill)
        );

        List<ProductDetail> actualProductDetails =
            weeklyProductsDetail.getProductsDetail();

        assertEquals(expectedProductsDetail, actualProductDetails);
    }

    @Test
    void noBills_getProductsDetail_returnsEmptyList() {
        List<ProductDetail> expectedProductsDetail = List.of();
        WeeklyProductsDetail weeklyProductsDetail = new WeeklyProductsDetail(List.of());

        List<ProductDetail> actualProductDetails =
            weeklyProductsDetail.getProductsDetail();

        assertEquals(expectedProductsDetail, actualProductDetails);
    }
}

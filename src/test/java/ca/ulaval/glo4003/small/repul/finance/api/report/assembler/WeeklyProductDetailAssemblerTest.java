package ca.ulaval.glo4003.small.repul.finance.api.report.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.fixture.finance.MealKitBillFixture;
import ca.ulaval.glo4003.repul.finance.api.report.assembler.WeeklyProductDetailAssembler;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ProductDetailResponse;
import ca.ulaval.glo4003.repul.finance.domain.bill.Bill;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeeklyProductDetailAssemblerTest {

    private final MealKitBillFixture mealKitBillFixture = new MealKitBillFixture();
    private final Bill BILL = mealKitBillFixture.build();
    private final Bill OTHER_BILL = mealKitBillFixture.build();
    private final ProductDetail PRODUCT_DETAIL = new ProductDetail(
        BILL.getDetail().getAsMap()
    );
    private final ProductDetail OTHER_PRODUCT_DETAIL = new ProductDetail(
        OTHER_BILL.getDetail().getAsMap()
    );
    private WeeklyProductDetailAssembler weeklyProductDetailAssembler;

    @BeforeEach
    void setup() {
        weeklyProductDetailAssembler = new WeeklyProductDetailAssembler();
    }

    @Test
    void emptyList_toResponse_returnsEmptyList() {
        List<ProductDetailResponse> actualProductDetailResponses =
            weeklyProductDetailAssembler.toResponse(List.of());

        assertTrue(actualProductDetailResponses.isEmpty());
    }

    @Test
    void twoProductDetails_toResponse_returnsListOfProductDetailResponse() {
        List<ProductDetailResponse> expectedProductDetailResponses = List.of(
            new ProductDetailResponse(PRODUCT_DETAIL.getAsMap()),
            new ProductDetailResponse(OTHER_PRODUCT_DETAIL.getAsMap())
        );

        List<ProductDetailResponse> actualProductDetailResponses =
            weeklyProductDetailAssembler.toResponse(
                List.of(PRODUCT_DETAIL, OTHER_PRODUCT_DETAIL)
            );

        assertEquals(expectedProductDetailResponses, actualProductDetailResponses);
    }
}

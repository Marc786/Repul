package ca.ulaval.glo4003.small.repul.finance.domain.report.weekly_summaries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ProductDetailTest {

    private final Map<String, String> DETAILS = Map.of("key", "value");

    @Test
    void getAsMap_returnsMap() {
        ProductDetail productDetail = new ProductDetail(DETAILS);

        Map<String, String> actualDetails = productDetail.getAsMap();

        assertEquals(DETAILS, actualDetails);
    }
}

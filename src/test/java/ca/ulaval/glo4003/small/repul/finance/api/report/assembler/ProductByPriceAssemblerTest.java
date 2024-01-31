package ca.ulaval.glo4003.small.repul.finance.api.report.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.api.report.assembler.ProductByPriceAssembler;
import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ProductByPriceResponse;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.ProductByPrice;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductByPriceAssemblerTest {

    private final ProductByPrice PRODUCT_BY_PRICE = new ProductByPrice(
        new Amount(1200.0),
        2
    );
    private final ProductByPrice OTHER_PRODUCT_BY_PRICE = new ProductByPrice(
        new Amount(300.0),
        3
    );
    private ProductByPriceAssembler productByPriceAssembler;

    @BeforeEach
    void setup() {
        productByPriceAssembler = new ProductByPriceAssembler();
    }

    @Test
    void emptyList_toResponse_returnsEmptyList() {
        List<ProductByPriceResponse> productsSoldByPricesResponses =
            productByPriceAssembler.toResponse(List.of());

        assertTrue(productsSoldByPricesResponses.isEmpty());
    }

    @Test
    void twoMealKitsSold_toResponse_returnsListOfMealKitsSoldResponse() {
        List<ProductByPrice> productsSoldByPrices = List.of(
            PRODUCT_BY_PRICE,
            OTHER_PRODUCT_BY_PRICE
        );
        List<ProductByPriceResponse> expectedProductByPriceResponses = List.of(
            new ProductByPriceResponse(
                PRODUCT_BY_PRICE.getAmount().value(),
                PRODUCT_BY_PRICE.getNumberOfProducts()
            ),
            new ProductByPriceResponse(
                OTHER_PRODUCT_BY_PRICE.getAmount().value(),
                OTHER_PRODUCT_BY_PRICE.getNumberOfProducts()
            )
        );

        List<ProductByPriceResponse> productsSoldByPricesResponses =
            productByPriceAssembler.toResponse(productsSoldByPrices);

        assertEquals(expectedProductByPriceResponses, productsSoldByPricesResponses);
    }
}

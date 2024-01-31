package ca.ulaval.glo4003.repul.finance.api.report.assembler;

import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ProductByPriceResponse;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.ProductByPrice;
import java.util.List;

public class ProductByPriceAssembler {

    public List<ProductByPriceResponse> toResponse(
        List<ProductByPrice> mealKitsSoldByPrice
    ) {
        return mealKitsSoldByPrice.stream().map(this::toResponse).toList();
    }

    private ProductByPriceResponse toResponse(ProductByPrice productByPrice) {
        return new ProductByPriceResponse(
            productByPrice.getAmount().value(),
            productByPrice.getNumberOfProducts()
        );
    }
}

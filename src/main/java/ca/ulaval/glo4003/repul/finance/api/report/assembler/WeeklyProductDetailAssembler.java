package ca.ulaval.glo4003.repul.finance.api.report.assembler;

import ca.ulaval.glo4003.repul.finance.api.report.dto.response.ProductDetailResponse;
import ca.ulaval.glo4003.repul.finance.domain.report.weekly_summaries.ProductDetail;
import java.util.List;

public class WeeklyProductDetailAssembler {

    public List<ProductDetailResponse> toResponse(List<ProductDetail> productsDetail) {
        return productsDetail.stream().map(this::toResponse).toList();
    }

    private ProductDetailResponse toResponse(ProductDetail productDetail) {
        return new ProductDetailResponse(productDetail.getAsMap());
    }
}

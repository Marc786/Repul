package ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.ReportBill;
import java.util.ArrayList;
import java.util.List;

public class SemesterProductsByPrice {

    private List<ProductByPrice> productsByPrice;

    public SemesterProductsByPrice(List<ReportBill> bills) {
        generateProductsByPrice(bills);
    }

    public List<ProductByPrice> getProductsByPrice() {
        return productsByPrice;
    }

    private void generateProductsByPrice(List<ReportBill> bills) {
        productsByPrice = new ArrayList<>();

        bills
            .stream()
            .map(ReportBill::getPrice)
            .forEach(billPrice -> {
                List<Amount> existingBillPrices = findExistingBillPrices();
                if (existingBillPrices.contains(billPrice)) {
                    incrementNumberOfProducts(billPrice);
                } else {
                    productsByPrice.add(new ProductByPrice(billPrice, 1));
                }
            });
    }

    private List<Amount> findExistingBillPrices() {
        return productsByPrice.stream().map(ProductByPrice::getAmount).toList();
    }

    private void incrementNumberOfProducts(Amount billPrice) {
        ProductByPrice existingProductByPrice = productsByPrice
            .stream()
            .filter(productByPrice -> productByPrice.getAmount().equals(billPrice))
            .findFirst()
            .orElseThrow();
        existingProductByPrice.setNumberOfProducts(
            existingProductByPrice.getNumberOfProducts() + 1
        );
    }
}

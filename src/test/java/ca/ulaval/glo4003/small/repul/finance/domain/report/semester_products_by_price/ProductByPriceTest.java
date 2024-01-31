package ca.ulaval.glo4003.small.repul.finance.domain.report.semester_products_by_price;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price.ProductByPrice;
import org.junit.jupiter.api.Test;

class ProductByPriceTest {

    private final Amount AMOUNT = new Amount(100.0);
    private final int NUMBER_OF_PRODUCTS = 2;
    private final int NEW_NUMBER_OF_PRODUCTS = 3;

    @Test
    void getAmount_returnsAmount() {
        ProductByPrice productByPrice = new ProductByPrice(AMOUNT, NUMBER_OF_PRODUCTS);

        Amount actualAmount = productByPrice.getAmount();

        assertEquals(AMOUNT, actualAmount);
    }

    @Test
    void getNumberOfProducts_returnsNumberOfProducts() {
        ProductByPrice productByPrice = new ProductByPrice(AMOUNT, NUMBER_OF_PRODUCTS);

        int actualNumberOfProducts = productByPrice.getNumberOfProducts();

        assertEquals(NUMBER_OF_PRODUCTS, actualNumberOfProducts);
    }

    @Test
    void setNumberOfProducts_setsNumberOfProducts() {
        ProductByPrice productByPrice = new ProductByPrice(AMOUNT, NUMBER_OF_PRODUCTS);

        productByPrice.setNumberOfProducts(NEW_NUMBER_OF_PRODUCTS);

        int actualNumberOfProducts = productByPrice.getNumberOfProducts();
        assertEquals(NEW_NUMBER_OF_PRODUCTS, actualNumberOfProducts);
    }
}

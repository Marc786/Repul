package ca.ulaval.glo4003.repul.finance.domain.report.semester_products_by_price;

import ca.ulaval.glo4003.lib.value_object.Amount;
import java.util.Objects;

public class ProductByPrice {

    private final Amount amount;
    private int numberOfProducts;

    public ProductByPrice(Amount amount, int numberOfProducts) {
        this.amount = amount;
        this.numberOfProducts = numberOfProducts;
    }

    public Amount getAmount() {
        return amount;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ProductByPrice) obj;
        return (
            Objects.equals(this.amount, that.amount) &&
            this.numberOfProducts == that.numberOfProducts
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, numberOfProducts);
    }
}

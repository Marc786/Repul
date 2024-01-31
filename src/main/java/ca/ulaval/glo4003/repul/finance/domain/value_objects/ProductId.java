package ca.ulaval.glo4003.repul.finance.domain.value_objects;

public class ProductId {

    private final String value;

    public ProductId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        ProductId otherProductId = (ProductId) other;
        return otherProductId.value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

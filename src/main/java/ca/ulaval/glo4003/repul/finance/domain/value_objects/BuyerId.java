package ca.ulaval.glo4003.repul.finance.domain.value_objects;

public class BuyerId {

    private final String value;

    public BuyerId(String value) {
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
        BuyerId otherBuyerId = (BuyerId) other;
        return otherBuyerId.value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

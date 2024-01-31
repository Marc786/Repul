package ca.ulaval.glo4003.lib.value_object;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Amount {

    private static final int NUMBER_OF_DECIMALS = 2;
    private BigDecimal value;

    public Amount(double value) {
        this.value = BigDecimal.valueOf(value);
    }

    public double value() {
        return value.doubleValue();
    }

    public Amount add(Amount other) {
        this.value =
            value
                .add(BigDecimal.valueOf(other.value()))
                .setScale(NUMBER_OF_DECIMALS, RoundingMode.HALF_EVEN);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Amount that = (Amount) obj;
        return Objects.equals(this.value.doubleValue(), that.value.doubleValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

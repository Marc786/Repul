package ca.ulaval.glo4003.small.lib.value_object;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.value_object.Amount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmountTest {

    private static final double AMOUNT_VALUE = 10.0;
    private static final double OTHER_AMOUNT_VALUE = 20.0;
    private Amount amount;
    private Amount otherAmount;

    @BeforeEach
    void setup() {
        amount = new Amount(AMOUNT_VALUE);
        otherAmount = new Amount(OTHER_AMOUNT_VALUE);
    }

    @Test
    void amount_addAmount_returnsSum() {
        double expectedSum = AMOUNT_VALUE + OTHER_AMOUNT_VALUE;
        Amount expectedAmount = new Amount(expectedSum);

        Amount actualAmount = amount.add(otherAmount);

        assertEquals(expectedAmount, actualAmount);
    }
}

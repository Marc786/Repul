package ca.ulaval.glo4003.small.repul.subscription.domain.subscription.attribute;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.repul.subscription.application.exception.InvalidFrequencyException;
import ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object.Frequency;
import org.junit.jupiter.api.Test;

class FrequencyTest {

    private static final String INVALID_FREQUENCY = "a frequency";
    private static final String WEEKLY_FREQUENCY_STRING = Frequency.WEEKLY.toString();

    @Test
    void fromString_returnsFrequency() {
        Frequency frequency = Frequency.fromString(WEEKLY_FREQUENCY_STRING);

        assertEquals(Frequency.WEEKLY, frequency);
    }

    @Test
    void invalidFrequency_fromString_throwsInvalidFrequencyException() {
        assertThrows(
            InvalidFrequencyException.class,
            () -> Frequency.fromString(INVALID_FREQUENCY)
        );
    }
}

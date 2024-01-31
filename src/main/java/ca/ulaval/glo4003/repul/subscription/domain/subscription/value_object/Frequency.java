package ca.ulaval.glo4003.repul.subscription.domain.subscription.value_object;

import ca.ulaval.glo4003.repul.subscription.application.exception.InvalidFrequencyException;
import java.util.Arrays;

public enum Frequency {
    WEEKLY("weekly"),
    SPORADIC("sporadic");

    private final String value;

    Frequency(String value) {
        this.value = value;
    }

    public static Frequency fromString(String input) {
        return Arrays
            .stream(values())
            .filter(frequency -> frequency.value.equalsIgnoreCase(input))
            .findFirst()
            .orElseThrow(() -> new InvalidFrequencyException(input));
    }

    @Override
    public String toString() {
        return value;
    }
}

package ca.ulaval.glo4003.lib.semester;

import static ca.ulaval.glo4003.constant.Constants.Path.SEMESTER_CODE_REGEX;

import ca.ulaval.glo4003.lib.semester.exception.InvalidSemesterCodeException;
import java.util.regex.Pattern;

public class SemesterCode {

    private final String value;

    public SemesterCode(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!Pattern.matches(SEMESTER_CODE_REGEX, value)) {
            throw new InvalidSemesterCodeException(value);
        }
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SemesterCode other = (SemesterCode) obj;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

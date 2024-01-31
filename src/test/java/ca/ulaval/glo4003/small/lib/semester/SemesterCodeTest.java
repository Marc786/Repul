package ca.ulaval.glo4003.small.lib.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.semester.exception.InvalidSemesterCodeException;
import org.junit.jupiter.api.Test;

class SemesterCodeTest {

    @Test
    void givenValidSemesterCode_whenCreatingSemesterCode_thenSemesterCodeIsCreated() {
        String validSemesterCode = "H23";

        SemesterCode semesterCode = new SemesterCode(validSemesterCode);

        assertEquals(validSemesterCode, semesterCode.toString());
    }

    @Test
    void givenInvalidSemesterCode_whenCreatingSemesterCode_thenInvalidSemesterCodeExceptionIsThrown() {
        String invalidSemesterCode = "invalid";

        assertThrows(
            InvalidSemesterCodeException.class,
            () -> new SemesterCode(invalidSemesterCode)
        );
    }
}

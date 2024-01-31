package ca.ulaval.glo4003.small.lib.date;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.lib.date.DateUtils;
import ca.ulaval.glo4003.lib.date.exception.InvalidDateFormatException;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

    private static final LocalDate DATE = LocalDate.of(2023, 1, 1);
    private static final String DATE_STRING = "2023-01-01";
    private static final String DATE_INVALID_FORMAT = "invalidDate";

    @Test
    void dateWithinRange_isDateBetweenRangeAndNow_returnsTrue() {
        LocalDate targetDate = LocalDate.now().plusDays(1);
        int rangeInDays = 2;

        boolean isDateWithinRange = DateUtils.isDateWithinRange(targetDate, rangeInDays);

        assertTrue(isDateWithinRange);
    }

    @Test
    void dateOutsideRange_isDateBetweenRangeAndNow_returnsFalse() {
        LocalDate targetDate = LocalDate.now().plusDays(5);
        int rangeInDays = 2;

        boolean isDateWithinRange = DateUtils.isDateWithinRange(targetDate, rangeInDays);

        assertFalse(isDateWithinRange);
    }

    @Test
    void validDateFormat_formatDateToLocalDate_returnsLocalDate() {
        LocalDate actualDate = DateUtils.formatDateToLocalDate(DATE_STRING);

        assertEquals(DATE, actualDate);
    }

    @Test
    void invalidDateFormat_formatDateToLocalDate_throwsInvalidDateFormatException() {
        Assertions.assertThrows(
            InvalidDateFormatException.class,
            () -> DateUtils.formatDateToLocalDate(DATE_INVALID_FORMAT)
        );
    }
}

package ca.ulaval.glo4003.small.lib.semester;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SemesterTest {

    private static final SemesterCode CODE = new SemesterCode("A23");
    private static final LocalDate START_DATE = LocalDate.of(2023, 9, 4);
    private static final LocalDate END_DATE = LocalDate.of(2023, 12, 15);
    private static final LocalDate IN_BOUND_DATE = LocalDate.of(2023, 10, 4);
    private static final LocalDate OUT_OF_BOUND_DATE = LocalDate.of(2023, 1, 4);
    private Semester semester;

    @BeforeEach
    void setup() {
        semester = new Semester(CODE, START_DATE, END_DATE);
    }

    @Test
    void inBoundDate_isDateInBounds_returnsTrue() {
        boolean dateIsInBounds = semester.isDateInBounds(IN_BOUND_DATE);

        assertTrue(dateIsInBounds);
    }

    @Test
    void outOfBoundDate_isDateInBounds_returnsFalse() {
        boolean dateIsInBounds = semester.isDateInBounds(OUT_OF_BOUND_DATE);

        assertFalse(dateIsInBounds);
    }

    @Test
    void startDate_isDateInBounds_returnsTrue() {
        boolean dateIsInBounds = semester.isDateInBounds(START_DATE);

        assertTrue(dateIsInBounds);
    }

    @Test
    void endDate_isDateInBounds_returnsTrue() {
        boolean dateIsInBounds = semester.isDateInBounds(END_DATE);

        assertTrue(dateIsInBounds);
    }

    @Test
    void getWeekNumbers_returnsValidWeekNumbers() {
        List<Integer> expectedWeekNumbers = getExpectedWeekNumbers();

        List<Integer> actualWeekNumbers = semester.getWeekNumbers();

        assertEquals(expectedWeekNumbers, actualWeekNumbers);
    }

    private List<Integer> getExpectedWeekNumbers() {
        List<Integer> expectedWeekNumbers = new ArrayList<>();

        for (
            int weekNumber = START_DATE.get(WeekFields.ISO.weekOfWeekBasedYear());
            weekNumber <= END_DATE.get(WeekFields.ISO.weekOfWeekBasedYear());
            weekNumber++
        ) {
            expectedWeekNumbers.add(weekNumber);
        }

        return expectedWeekNumbers;
    }
}

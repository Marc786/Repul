package ca.ulaval.glo4003.small.lib.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.semester.Semesters;
import ca.ulaval.glo4003.lib.semester.exception.SemesterCodeNotFoundException;
import ca.ulaval.glo4003.lib.semester.exception.SemesterNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SemestersTest {

    private static final SemesterCode SEMESTER_CODE = new SemesterCode("H23");
    private static final SemesterCode INVALID_SEMESTER_CODE = new SemesterCode("H20");
    private static final LocalDate SEMESTER_START_DATE = LocalDate.of(2023, 1, 15);
    private static final LocalDate SEMESTER_END_DATE = LocalDate.of(2023, 5, 15);
    private static final LocalDate IN_BOUND_DATE = LocalDate.of(2023, 3, 1);
    private static final LocalDate BEFORE_SEMESTER_DATE = LocalDate.of(2023, 1, 1);
    private static final LocalDate AFTER_SEMESTER_DATE = LocalDate.of(2023, 6, 1);

    private Semester semester;
    private Semesters semesters;

    @BeforeEach
    void setup() {
        semester = new Semester(SEMESTER_CODE, SEMESTER_START_DATE, SEMESTER_END_DATE);

        semesters = new Semesters(List.of(semester));
    }

    @Test
    void inBoundDate_findSemesterByDate_returnsSemester() {
        Semester actualSemester = semesters.findSemesterByDate(IN_BOUND_DATE);

        assertEquals(semester, actualSemester);
    }

    @Test
    void startDate_findSemesterByDate_returnsSemester() {
        Semester actualSemester = semesters.findSemesterByDate(SEMESTER_START_DATE);

        assertEquals(semester, actualSemester);
    }

    @Test
    void endDate_findSemesterByDate_returnsSemester() {
        Semester actualSemester = semesters.findSemesterByDate(SEMESTER_END_DATE);

        assertEquals(semester, actualSemester);
    }

    @Test
    void beforeSemesterDate_findSemesterByDate_throwsSemesterNotFoundException() {
        assertThrows(
            SemesterNotFoundException.class,
            () -> semesters.findSemesterByDate(BEFORE_SEMESTER_DATE)
        );
    }

    @Test
    void afterSemesterDate_findSemesterByDate_throwsSemesterNotFoundException() {
        assertThrows(
            SemesterNotFoundException.class,
            () -> semesters.findSemesterByDate(AFTER_SEMESTER_DATE)
        );
    }

    @Test
    void beforeSemesterDate_findPreviousSemesterByDate_throwsSemesterNotFoundException() {
        assertThrows(
            SemesterNotFoundException.class,
            () -> semesters.findPreviousSemesterByDate(BEFORE_SEMESTER_DATE)
        );
    }

    @Test
    void inBoundDate_findPreviousSemesterByDate_throwsSemesterNotFoundException() {
        assertThrows(
            SemesterNotFoundException.class,
            () -> semesters.findPreviousSemesterByDate(IN_BOUND_DATE)
        );
    }

    @Test
    void afterSemesterDate_findPreviousSemesterByDate_returnsSemester() {
        Semester actualSemester = semesters.findPreviousSemesterByDate(
            AFTER_SEMESTER_DATE
        );

        assertEquals(semester, actualSemester);
    }

    @Test
    void beforeSemesterDate_findNextSemesterByDate_returnsSemester() {
        Semester actualSemester = semesters.findNextSemesterByDate(BEFORE_SEMESTER_DATE);

        assertEquals(semester, actualSemester);
    }

    @Test
    void inBoundDate_findNextSemesterByDate_throwsSemesterNotFoundException() {
        assertThrows(
            SemesterNotFoundException.class,
            () -> semesters.findNextSemesterByDate(IN_BOUND_DATE)
        );
    }

    @Test
    void afterSemesterDate_findNextSemesterByDate_throwsSemesterNotFoundException() {
        assertThrows(
            SemesterNotFoundException.class,
            () -> semesters.findNextSemesterByDate(AFTER_SEMESTER_DATE)
        );
    }

    @Test
    void getSemesterByCode_returnsSemester() {
        Semester actualSemester = semesters.getSemesterByCode(SEMESTER_CODE);

        assertEquals(semester, actualSemester);
    }

    @Test
    void invalidSemesterCode_getSemesterByCode_throwsSemesterCodeNotFoundException() {
        assertThrows(
            SemesterCodeNotFoundException.class,
            () -> semesters.getSemesterByCode(INVALID_SEMESTER_CODE)
        );
    }
}

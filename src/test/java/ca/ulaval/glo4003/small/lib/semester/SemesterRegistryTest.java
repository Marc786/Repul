package ca.ulaval.glo4003.small.lib.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.semester.exception.SemesterNotFoundException;
import ca.ulaval.glo4003.lib.semester.registry.SemesterRegistry;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SemesterRegistryTest {

    public static final String JSON_SEMESTERS =
        "src/test/java/ca/ulaval/glo4003/small/lib/semester/semesters.json";
    private static final SemesterCode FIRST_SEMESTER_CODE = new SemesterCode("H21");
    private static final SemesterCode SECOND_SEMESTER_CODE = new SemesterCode("A21");
    private static final LocalDate FIRST_SEMESTER_START_DATE = LocalDate.of(2021, 1, 15);
    private static final LocalDate FIRST_SEMESTER_END_DATE = LocalDate.of(2021, 5, 15);
    private static final LocalDate SECOND_SEMESTER_START_DATE = LocalDate.of(2021, 7, 4);
    private static final LocalDate SECOND_SEMESTER_END_DATE = LocalDate.of(2021, 12, 15);
    private static final LocalDate BEFORE_FIRST_SEMESTER_DATE = LocalDate.of(2021, 1, 1);
    private static final LocalDate IN_FIRST_SEMESTER_DATE = LocalDate.of(2021, 3, 1);
    private static final LocalDate BETWEEN_SEMESTERS_DATE = LocalDate.of(2021, 6, 1);
    private static final LocalDate IN_SECOND_SEMESTER_DATE = LocalDate.of(2021, 9, 1);
    private static final LocalDate AFTER_SECOND_SEMESTER_DATE = LocalDate.of(
        2021,
        12,
        30
    );

    private Semester firstSemester;
    private Semester secondSemester;
    private SemesterRegistry semesterRegistry;

    @BeforeEach
    void setup() {
        firstSemester =
            new Semester(
                FIRST_SEMESTER_CODE,
                FIRST_SEMESTER_START_DATE,
                FIRST_SEMESTER_END_DATE
            );
        secondSemester =
            new Semester(
                SECOND_SEMESTER_CODE,
                SECOND_SEMESTER_START_DATE,
                SECOND_SEMESTER_END_DATE
            );
        semesterRegistry = new SemesterRegistry(JSON_SEMESTERS);
    }

    @Test
    void beforeFirstSemesterDate_findCurrentOrNextSemester_returnsFirstSemester() {
        Semester actualSemester = semesterRegistry.findCurrentOrNextSemester(
            BEFORE_FIRST_SEMESTER_DATE
        );

        assertEquals(firstSemester, actualSemester);
    }

    @Test
    void inFirstSemesterDate_findCurrentOrNextSemester_returnsFirstSemester() {
        Semester actualSemester = semesterRegistry.findCurrentOrNextSemester(
            IN_FIRST_SEMESTER_DATE
        );

        assertEquals(firstSemester, actualSemester);
    }

    @Test
    void betweenSemestersDate_findCurrentOrNextSemester_returnsSecondSemester() {
        Semester actualSemester = semesterRegistry.findCurrentOrNextSemester(
            BETWEEN_SEMESTERS_DATE
        );

        assertEquals(secondSemester, actualSemester);
    }

    @Test
    void inSecondSemesterDate_findCurrentOrNextSemester_returnsSecondSemester() {
        Semester actualSemester = semesterRegistry.findCurrentOrNextSemester(
            IN_SECOND_SEMESTER_DATE
        );

        assertEquals(secondSemester, actualSemester);
    }

    @Test
    void afterSecondSemesterDate_findCurrentOrNextSemester_throwsSemesterNotFoundException() {
        assertThrows(
            SemesterNotFoundException.class,
            () -> semesterRegistry.findCurrentOrNextSemester(AFTER_SECOND_SEMESTER_DATE)
        );
    }

    @Test
    void beforeFirstSemesterDate_findCurrentOrPreviousSemester_throwsSemesterNotFoundException() {
        assertThrows(
            SemesterNotFoundException.class,
            () ->
                semesterRegistry.findCurrentOrPreviousSemester(BEFORE_FIRST_SEMESTER_DATE)
        );
    }

    @Test
    void inFirstSemesterDate_findCurrentOrPreviousSemester_returnsFirstSemester() {
        Semester actualSemester = semesterRegistry.findCurrentOrPreviousSemester(
            IN_FIRST_SEMESTER_DATE
        );

        assertEquals(firstSemester, actualSemester);
    }

    @Test
    void betweenSemestersDate_findCurrentOrPreviousSemester_returnsFirstSemester() {
        Semester actualSemester = semesterRegistry.findCurrentOrPreviousSemester(
            BETWEEN_SEMESTERS_DATE
        );

        assertEquals(firstSemester, actualSemester);
    }

    @Test
    void inSecondSemesterDate_findCurrentOrPreviousSemester_returnsSecondSemester() {
        Semester actualSemester = semesterRegistry.findCurrentOrPreviousSemester(
            IN_SECOND_SEMESTER_DATE
        );

        assertEquals(secondSemester, actualSemester);
    }

    @Test
    void afterSecondSemesterDate_findCurrentOrPreviousSemester_returnsSecondSemester() {
        Semester actualSemester = semesterRegistry.findCurrentOrPreviousSemester(
            AFTER_SECOND_SEMESTER_DATE
        );

        assertEquals(secondSemester, actualSemester);
    }
}

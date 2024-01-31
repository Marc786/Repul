package ca.ulaval.glo4003.small.lib.semester;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.semester.Semesters;
import ca.ulaval.glo4003.lib.semester.registry.SemesterAssembler;
import ca.ulaval.glo4003.lib.semester.registry.SemesterJson;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class SemesterAssemblerTest {

    private static final SemesterCode SEMESTER_CODE = new SemesterCode("H21");
    private static final LocalDate START_DATE = LocalDate.of(2021, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2021, 4, 30);
    private static final Semester SEMESTER = new Semester(
        SEMESTER_CODE,
        START_DATE,
        END_DATE
    );
    private static final Semesters SEMESTERS = new Semesters(List.of(SEMESTER));
    private static final SemesterJson SEMESTER_JSON = new SemesterJson(
        SEMESTER_CODE.toString(),
        START_DATE.toString(),
        END_DATE.toString()
    );
    private static final SemesterAssembler SEMESTER_ASSEMBLER = new SemesterAssembler();

    @Test
    void fromJson_returnsSemester() {
        Semesters actualSemesters = SEMESTER_ASSEMBLER.fromJson(List.of(SEMESTER_JSON));

        assertEquals(SEMESTERS, actualSemesters);
    }
}

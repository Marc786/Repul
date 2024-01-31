package ca.ulaval.glo4003.lib.semester.registry.finder;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import java.time.LocalDate;

public interface ReportSemesterFinder {
    Semester findCurrentOrPreviousSemester(LocalDate date);
    Semester getSemesterByCode(SemesterCode code);
}

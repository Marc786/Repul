package ca.ulaval.glo4003.lib.semester.registry;

import ca.ulaval.glo4003.lib.date.DateUtils;
import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import ca.ulaval.glo4003.lib.semester.Semesters;
import java.util.List;

public class SemesterAssembler {

    public Semesters fromJson(List<SemesterJson> semesterJsonList) {
        return new Semesters(
            semesterJsonList
                .stream()
                .map(semesterJson ->
                    new Semester(
                        new SemesterCode(semesterJson.semesterCode()),
                        DateUtils.formatDateToLocalDate(semesterJson.startDate()),
                        DateUtils.formatDateToLocalDate(semesterJson.endDate())
                    )
                )
                .toList()
        );
    }
}

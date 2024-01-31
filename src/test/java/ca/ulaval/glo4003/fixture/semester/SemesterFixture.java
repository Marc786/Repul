package ca.ulaval.glo4003.fixture.semester;

import ca.ulaval.glo4003.lib.semester.Semester;
import ca.ulaval.glo4003.lib.semester.SemesterCode;
import java.time.LocalDate;

public class SemesterFixture {

    private SemesterCode code;
    private LocalDate startDate;
    private LocalDate endDate;

    public Semester build() {
        return new Semester(code, startDate, endDate);
    }

    public SemesterFixture withCode(SemesterCode code) {
        this.code = code;
        return this;
    }

    public SemesterFixture withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public SemesterFixture withEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}

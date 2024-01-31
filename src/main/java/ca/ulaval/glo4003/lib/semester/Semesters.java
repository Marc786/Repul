package ca.ulaval.glo4003.lib.semester;

import ca.ulaval.glo4003.lib.semester.exception.SemesterCodeNotFoundException;
import ca.ulaval.glo4003.lib.semester.exception.SemesterNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Semesters {

    private final List<Semester> elements;

    public Semesters(List<Semester> elements) {
        this.elements = elements;
    }

    public Semester findSemesterByDate(LocalDate date) {
        return elements
            .stream()
            .filter(semester -> semester.isDateInBounds(date))
            .findFirst()
            .orElseThrow(() -> new SemesterNotFoundException(date));
    }

    public Semester findPreviousSemesterByDate(LocalDate date) {
        return elements
            .stream()
            .filter(semester -> date.isAfter(semester.endDate()))
            .max(Comparator.comparing(Semester::endDate))
            .orElseThrow(() -> new SemesterNotFoundException(date));
    }

    public Semester findNextSemesterByDate(LocalDate date) {
        return elements
            .stream()
            .filter(semester -> date.isBefore(semester.startDate()))
            .min(Comparator.comparing(Semester::startDate))
            .orElseThrow(() -> new SemesterNotFoundException(date));
    }

    public Semester getSemesterByCode(SemesterCode code) {
        return elements
            .stream()
            .filter(semester -> semester.code().equals(code))
            .findFirst()
            .orElseThrow(() -> new SemesterCodeNotFoundException(code));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Semesters that = (Semesters) obj;

        return this.elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }
}

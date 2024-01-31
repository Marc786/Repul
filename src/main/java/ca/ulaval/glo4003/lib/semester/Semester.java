package ca.ulaval.glo4003.lib.semester;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Semester {

    private final SemesterCode code;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Semester(SemesterCode code, LocalDate startDate, LocalDate endDate) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isDateInBounds(LocalDate date) {
        return (
            date.isEqual(startDate) ||
            date.isEqual(endDate) ||
            (date.isAfter(startDate) && date.isBefore(endDate))
        );
    }

    public List<Integer> getWeekNumbers() {
        List<Integer> weekNumbers = new ArrayList<>();
        int startWeekNumber = startDate.get(
            WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
        );
        int endWeekNumber = endDate.get(
            WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()
        );
        while (startWeekNumber <= endWeekNumber) {
            weekNumbers.add(startWeekNumber);
            startWeekNumber++;
        }

        return weekNumbers;
    }

    public SemesterCode code() {
        return code;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Semester that = (Semester) obj;
        return (
            Objects.equals(this.code, that.code) &&
            Objects.equals(this.startDate, that.startDate) &&
            Objects.equals(this.endDate, that.endDate)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, startDate, endDate);
    }
}

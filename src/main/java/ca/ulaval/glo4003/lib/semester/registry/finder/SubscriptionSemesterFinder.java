package ca.ulaval.glo4003.lib.semester.registry.finder;

import ca.ulaval.glo4003.lib.semester.Semester;
import java.time.LocalDate;

public interface SubscriptionSemesterFinder {
    Semester findCurrentOrNextSemester(LocalDate date);
}

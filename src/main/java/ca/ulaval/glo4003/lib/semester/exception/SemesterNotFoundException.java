package ca.ulaval.glo4003.lib.semester.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import java.time.LocalDate;

public class SemesterNotFoundException extends ItemNotFoundException {

    private static final String ERROR_MESSAGE = "Semester at date %s not found";

    public SemesterNotFoundException(LocalDate date) {
        super(String.format(ERROR_MESSAGE, date));
    }
}

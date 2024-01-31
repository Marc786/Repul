package ca.ulaval.glo4003.lib.semester.exception;

import ca.ulaval.glo4003.exception.ItemNotFoundException;
import ca.ulaval.glo4003.lib.semester.SemesterCode;

public class SemesterCodeNotFoundException extends ItemNotFoundException {

    public static final String SEMESTER_CODE_NOT_FOUND = "Semester code %s not found";

    public SemesterCodeNotFoundException(SemesterCode code) {
        super(String.format(SEMESTER_CODE_NOT_FOUND, code));
    }
}

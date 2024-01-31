package ca.ulaval.glo4003.lib.semester.exception;

import ca.ulaval.glo4003.exception.InvalidInputException;

public class InvalidSemesterCodeException extends InvalidInputException {

    public static final String SEMESTER_CODE_IS_INVALID = "Semester code %s is invalid";

    public InvalidSemesterCodeException(String code) {
        super(String.format(SEMESTER_CODE_IS_INVALID, code));
    }
}

package ca.ulaval.glo4003.repul.account.domain.customer.exception.student_card;

import jakarta.ws.rs.BadRequestException;

public class InvalidStudentCardNumberException extends BadRequestException {

    private static final String ERROR_MESSAGE =
        "The student card number must be a number of 9 digits";

    public InvalidStudentCardNumberException() {
        super(ERROR_MESSAGE);
    }
}

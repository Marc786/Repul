package ca.ulaval.glo4003.repul.account.domain.customer.value_object;

import ca.ulaval.glo4003.repul.account.domain.customer.exception.student_card.InvalidStudentCardNumberException;

public record StudentCard(String value) {
    public static final String STUDENT_CARD_NUMBER_REGEX = "^[0-9]{9}$";

    public StudentCard {
        validate(value);
    }

    private void validate(String studentCardNumber) {
        if (!isStudentCardNumberValid(studentCardNumber)) {
            throw new InvalidStudentCardNumberException();
        }
    }

    private boolean isStudentCardNumberValid(String studentCardNumber) {
        return studentCardNumber.matches(STUDENT_CARD_NUMBER_REGEX);
    }
}

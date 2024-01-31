package ca.ulaval.glo4003.small.repul.account.domain.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.repul.account.domain.customer.exception.student_card.InvalidStudentCardNumberException;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import org.junit.jupiter.api.Test;

class StudentCardTest {

    private static final String STUDENT_CARD_NUMBER = "123456789";
    private static final String INVALID_STUDENT_CARD_NUMBER = "invalid";

    @Test
    void validStudentCardNumber_createStudentCard_studentCardIsCreated() {
        StudentCard studentCard = new StudentCard(STUDENT_CARD_NUMBER);

        assertEquals(STUDENT_CARD_NUMBER, studentCard.value());
    }

    @Test
    void invalidStudentCardNumber_createStudentCard_throwsInvalidStudentCardNumberException() {
        assertThrows(
            InvalidStudentCardNumberException.class,
            () -> new StudentCard(INVALID_STUDENT_CARD_NUMBER)
        );
    }
}

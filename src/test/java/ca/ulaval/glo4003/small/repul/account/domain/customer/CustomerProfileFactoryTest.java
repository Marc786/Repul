package ca.ulaval.glo4003.small.repul.account.domain.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4003.fixture.account.CustomerProfileFixture;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileFactory;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.InvalidBirthDateException;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.InvalidIdulException;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.InvalidULavalEmailException;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.Gender;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CustomerProfileFactoryTest {

    private static final String EMAIL_STRING = "abc.def.1@ulaval.ca";
    private static final String FIRST_NAME_STRING = "Bob";
    private static final String LAST_NAME_STRING = "Martin";
    private static final String GENDER_STRING = "male";
    private static final String IDUL_STRING = "bmart007";
    private static final Gender GENDER = Gender.fromString(GENDER_STRING);
    private static final int BIRTH_DATE_YEAR = 1990;
    private static final int BIRTH_DATE_MONTH = 1;
    private static final int BIRTH_DATE_DAY = 1;
    private static final EmailAddress EMAIL = new EmailAddress(EMAIL_STRING);
    private static final Name USER_NAME = new Name(FIRST_NAME_STRING, LAST_NAME_STRING);
    private static final String STUDENT_CARD_NUMBER = "123456789";
    private static final StudentCard STUDENT_CARD = new StudentCard(STUDENT_CARD_NUMBER);
    private static final CustomerProfileId CUSTOMER_ID = new CustomerProfileId(
        IDUL_STRING
    );
    private static final LocalDate BIRTH_DATE = LocalDate.of(
        BIRTH_DATE_YEAR,
        BIRTH_DATE_MONTH,
        BIRTH_DATE_DAY
    );
    private static final EmailAddress INVALID_EMAIL = new EmailAddress("invalid");
    private static final LocalDate INVALID_BIRTH_DATE = LocalDate.of(2050, 1, 1);
    private static final CustomerProfileId INVALID_CUSTOMER_ID = new CustomerProfileId(
        "invalid"
    );

    private final CustomerProfileFixture customerProfileFixture =
        new CustomerProfileFixture();
    private final CustomerProfileFactory customerProfileFactory =
        new CustomerProfileFactory();

    @Test
    void validInformation_createCustomer_customerIsCreated() {
        CustomerProfile expectedCustomerProfile = customerProfileFixture
            .withEmail(EMAIL)
            .withUserName(USER_NAME)
            .withBirthDate(BIRTH_DATE)
            .withGender(GENDER)
            .build();

        CustomerProfile actualCustomerProfile = customerProfileFactory.create(
            EMAIL,
            USER_NAME,
            BIRTH_DATE,
            GENDER,
            CUSTOMER_ID,
            STUDENT_CARD
        );

        assertEquals(expectedCustomerProfile, actualCustomerProfile);
    }

    @Test
    void invalidEmail_createCustomer_invalidUlavalEmailExceptionIsThrown() {
        assertThrows(
            InvalidULavalEmailException.class,
            () ->
                customerProfileFactory.create(
                    INVALID_EMAIL,
                    USER_NAME,
                    BIRTH_DATE,
                    GENDER,
                    CUSTOMER_ID,
                    STUDENT_CARD
                )
        );
    }

    @Test
    void invalidBirthDate_createCustomer_invalidBirthDateIsThrown() {
        assertThrows(
            InvalidBirthDateException.class,
            () ->
                customerProfileFactory.create(
                    EMAIL,
                    USER_NAME,
                    INVALID_BIRTH_DATE,
                    GENDER,
                    CUSTOMER_ID,
                    STUDENT_CARD
                )
        );
    }

    @Test
    void invalidIdul_createCustomer_invalidIdulIsThrown() {
        assertThrows(
            InvalidIdulException.class,
            () ->
                customerProfileFactory.create(
                    EMAIL,
                    USER_NAME,
                    BIRTH_DATE,
                    GENDER,
                    INVALID_CUSTOMER_ID,
                    STUDENT_CARD
                )
        );
    }
}

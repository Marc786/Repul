package ca.ulaval.glo4003.repul.account.domain.customer;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup;
import static ca.ulaval.glo4003.constant.Constants.Validator.*;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.InvalidBirthDateException;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.InvalidIdulException;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.InvalidULavalEmailException;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.Gender;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import java.time.LocalDate;

public class CustomerProfileFactory {

    public CustomerProfile create(
        EmailAddress email,
        Name userName,
        LocalDate birthDate,
        Gender gender,
        CustomerProfileId customerProfileId,
        StudentCard studentCard
    ) {
        validateUlavalEmail(email);
        validateBirthDate(birthDate);
        validateIdul(customerProfileId);
        return new CustomerProfile(
            email,
            userName,
            birthDate,
            gender,
            customerProfileId,
            studentCard
        );
    }

    private void validateUlavalEmail(EmailAddress email) {
        if (!email.toString().matches(ULAVAL_EMAIL_REGEX)) {
            throw new InvalidULavalEmailException();
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (
            !birthDate
                .plusYears(USER_ACCOUNT_MINIMAL_AGE)
                .isBefore(LocalDate.now(ClockSetup.getClock()))
        ) {
            throw new InvalidBirthDateException();
        }
    }

    private void validateIdul(CustomerProfileId customerProfileId) {
        if (!customerProfileId.toString().matches(IDUL_REGEX)) {
            throw new InvalidIdulException();
        }
    }
}

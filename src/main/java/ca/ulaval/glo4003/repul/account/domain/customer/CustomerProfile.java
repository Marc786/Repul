package ca.ulaval.glo4003.repul.account.domain.customer;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.Gender;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class CustomerProfile {

    private final EmailAddress email;
    private final Name userName;
    private final LocalDate birthDate;
    private final Gender gender;
    private final CustomerProfileId customerProfileId;
    private final StudentCard studentCard;

    public CustomerProfile(
        EmailAddress email,
        Name userName,
        LocalDate birthDate,
        Gender gender,
        CustomerProfileId customerProfileId,
        StudentCard studentCard
    ) {
        this.email = email;
        this.userName = userName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.customerProfileId = customerProfileId;
        this.studentCard = studentCard;
    }

    public int getAge() {
        return Period.between(birthDate, LocalDate.now(ClockSetup.getClock())).getYears();
    }

    public EmailAddress getEmail() {
        return email;
    }

    public Name getName() {
        return userName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public CustomerProfileId getId() {
        return customerProfileId;
    }

    public StudentCard getStudentCard() {
        return studentCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerProfile customerProfile = (CustomerProfile) o;
        return (
            Objects.equals(email, customerProfile.email) &&
            Objects.equals(userName, customerProfile.userName) &&
            Objects.equals(birthDate, customerProfile.birthDate) &&
            gender == customerProfile.gender &&
            Objects.equals(customerProfileId, customerProfile.customerProfileId) &&
            Objects.equals(studentCard, customerProfile.studentCard)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            email,
            userName,
            birthDate,
            gender,
            customerProfileId,
            studentCard
        );
    }
}

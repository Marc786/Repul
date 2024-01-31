package ca.ulaval.glo4003.fixture.account;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.Gender;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import java.time.LocalDate;

public class CustomerProfileFixture {

    private EmailAddress email = new EmailAddress("abc@ulaval.ca");
    private Name userName = new Name("Bob", "Martin");
    private LocalDate birthDate = LocalDate.of(1900, 1, 1);
    private Gender gender = Gender.fromString("male");
    private CustomerProfileId customerProfileId = new CustomerProfileId("bmart007");
    private StudentCard studentCard = new StudentCard("123456789");

    public CustomerProfile build() {
        return new CustomerProfile(
            email,
            userName,
            birthDate,
            gender,
            customerProfileId,
            studentCard
        );
    }

    public CustomerProfileFixture withEmail(EmailAddress email) {
        this.email = email;
        return this;
    }

    public CustomerProfileFixture withUserName(Name userName) {
        this.userName = userName;
        return this;
    }

    public CustomerProfileFixture withBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public CustomerProfileFixture withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public CustomerProfileFixture withId(CustomerProfileId customerProfileId) {
        this.customerProfileId = customerProfileId;
        return this;
    }

    public CustomerProfileFixture withStudentCard(StudentCard studentCard) {
        this.studentCard = studentCard;
        return this;
    }
}

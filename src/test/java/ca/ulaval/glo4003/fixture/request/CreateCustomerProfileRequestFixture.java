package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.repul.account.api.customer.dto.request.CreateCustomerProfileRequest;

public class CreateCustomerProfileRequestFixture {

    private String idul = "ralol03";
    private String email = "raph03@ulaval.ca";
    private final String password = "123";
    private final String firstName = "raph";
    private final String lastName = "lol";
    private final String birthDate = "2000-09-15";
    private final String gender = "male";
    private String studentCardNumber = "123456789";

    public CreateCustomerProfileRequest build() {
        return new CreateCustomerProfileRequest(
            email,
            password,
            firstName,
            lastName,
            birthDate,
            gender,
            idul,
            studentCardNumber
        );
    }

    public CreateCustomerProfileRequestFixture withCustomerProfileId(String idul) {
        this.idul = idul;
        return this;
    }

    public CreateCustomerProfileRequestFixture withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateCustomerProfileRequestFixture withStudentCardNumber(
        String studentCardNumber
    ) {
        this.studentCardNumber = studentCardNumber;
        return this;
    }
}

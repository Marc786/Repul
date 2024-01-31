package ca.ulaval.glo4003.repul.account.api.customer.assembler;

import ca.ulaval.glo4003.repul.account.api.customer.dto.response.CustomerProfileResponse;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;

public class CustomerProfileAssembler {

    public CustomerProfileResponse toResponse(CustomerProfile customerProfile) {
        return new CustomerProfileResponse(
            customerProfile.getName().firstName(),
            customerProfile.getName().lastName(),
            customerProfile.getBirthDate().toString(),
            customerProfile.getGender().toString(),
            customerProfile.getAge(),
            customerProfile.getId().toString()
        );
    }
}

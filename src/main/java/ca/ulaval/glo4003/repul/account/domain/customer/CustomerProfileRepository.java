package ca.ulaval.glo4003.repul.account.domain.customer;

import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import java.util.List;

public interface CustomerProfileRepository {
    void save(CustomerProfile customerProfile);

    CustomerProfile findByIdul(CustomerProfileId customerProfileId);

    CustomerProfile findByStudentCard(StudentCard studentCard);

    List<CustomerProfile> findAll();
}

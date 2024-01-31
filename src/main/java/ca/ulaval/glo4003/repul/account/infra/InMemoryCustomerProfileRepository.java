package ca.ulaval.glo4003.repul.account.infra;

import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.CustomerProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import ca.ulaval.glo4003.repul.account.infra.cloner.CustomerProfileFastCloner;
import com.rits.cloning.Cloner;
import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerProfileRepository implements CustomerProfileRepository {

    private final Cloner cloner = new Cloner();
    private final List<CustomerProfile> customerProfiles = new ArrayList<>();

    public InMemoryCustomerProfileRepository() {
        cloner.registerFastCloner(CustomerProfile.class, new CustomerProfileFastCloner());
    }

    @Override
    public void save(CustomerProfile customerProfile) {
        removeIfExisting(customerProfile);
        customerProfiles.add(customerProfile);
    }

    @Override
    public CustomerProfile findByIdul(CustomerProfileId customerProfileId) {
        CustomerProfile customerProfileFound = customerProfiles
            .stream()
            .filter(customer -> customer.getId().equals(customerProfileId))
            .findFirst()
            .orElseThrow(CustomerProfileNotFoundException::new);
        return cloner.deepClone(customerProfileFound);
    }

    @Override
    public CustomerProfile findByStudentCard(StudentCard studentCard) {
        CustomerProfile customerProfileFound = customerProfiles
            .stream()
            .filter(customer -> customer.getStudentCard().equals(studentCard))
            .findFirst()
            .orElseThrow(CustomerProfileNotFoundException::new);
        return cloner.deepClone(customerProfileFound);
    }

    @Override
    public List<CustomerProfile> findAll() {
        return cloner.deepClone(customerProfiles);
    }

    private void removeIfExisting(CustomerProfile customerProfile) {
        customerProfiles.removeIf(existingCustomer ->
            existingCustomer.getId().equals(customerProfile.getId())
        );
    }
}

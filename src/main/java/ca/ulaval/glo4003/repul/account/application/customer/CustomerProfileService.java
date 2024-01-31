package ca.ulaval.glo4003.repul.account.application.customer;

import ca.ulaval.glo4003.event.account.customer.CustomerCreatedEvent;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileFactory;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfileRepository;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.CustomerProfileAlreadyExistsException;
import ca.ulaval.glo4003.repul.account.domain.customer.exception.CustomerProfileNotFoundException;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.Gender;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import java.time.LocalDate;

public class CustomerProfileService {

    private final CustomerProfileFactory customerProfileFactory;
    private final CustomerProfileRepository customerProfileRepository;
    private final CreateCustomerProfileObservable createCustomerProfileObservable;

    public CustomerProfileService(
        CustomerProfileFactory customerProfileFactory,
        CustomerProfileRepository customerProfileRepository,
        CreateCustomerProfileObservable createCustomerProfileObservable
    ) {
        this.customerProfileFactory = customerProfileFactory;
        this.customerProfileRepository = customerProfileRepository;
        this.createCustomerProfileObservable = createCustomerProfileObservable;
    }

    public CustomerProfileId createCustomer(
        EmailAddress email,
        Password password,
        Name userName,
        LocalDate birthDate,
        Gender gender,
        CustomerProfileId customerProfileId,
        StudentCard studentCard
    ) {
        if (doesCustomerAlreadyExist(customerProfileId, studentCard)) {
            throw new CustomerProfileAlreadyExistsException();
        }

        CustomerProfile customerProfile = customerProfileFactory.create(
            email,
            userName,
            birthDate,
            gender,
            customerProfileId,
            studentCard
        );

        customerProfileRepository.save(customerProfile);
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent(
            customerProfile.getId(),
            customerProfile.getEmail(),
            password
        );
        createCustomerProfileObservable.notifyCustomerCreated(customerCreatedEvent);

        return customerProfile.getId();
    }

    public CustomerProfile getCustomer(CustomerProfileId customerProfileId) {
        return customerProfileRepository.findByIdul(customerProfileId);
    }

    private boolean doesCustomerAlreadyExist(
        CustomerProfileId customerProfileId,
        StudentCard studentCard
    ) {
        return (
            doesCustomerProfileIdAlreadyExist(customerProfileId) ||
            doesStudentCardAlreadyExist(studentCard)
        );
    }

    private boolean doesCustomerProfileIdAlreadyExist(
        CustomerProfileId customerProfileId
    ) {
        try {
            customerProfileRepository.findByIdul(customerProfileId);
            return true;
        } catch (CustomerProfileNotFoundException e) {
            return false;
        }
    }

    private boolean doesStudentCardAlreadyExist(StudentCard studentCard) {
        try {
            customerProfileRepository.findByStudentCard(studentCard);
            return true;
        } catch (CustomerProfileNotFoundException e) {
            return false;
        }
    }
}

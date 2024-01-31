package ca.ulaval.glo4003.repul.account.infra.cloner;

import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.util.Map;

public class CustomerProfileFastCloner implements IFastCloner {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object clone(
        final Object t,
        final IDeepCloner cloner,
        final Map<Object, Object> clones
    ) {
        final CustomerProfile original = (CustomerProfile) t;
        return new CustomerProfile(
            original.getEmail(),
            original.getName(),
            original.getBirthDate(),
            original.getGender(),
            original.getId(),
            original.getStudentCard()
        );
    }
}

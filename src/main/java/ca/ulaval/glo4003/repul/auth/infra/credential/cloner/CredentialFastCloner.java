package ca.ulaval.glo4003.repul.auth.infra.credential.cloner;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.auth.domain.credential.Credential;
import ca.ulaval.glo4003.repul.auth.domain.credential.CredentialId;
import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.util.Map;

public class CredentialFastCloner implements IFastCloner {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object clone(
        final Object t,
        final IDeepCloner cloner,
        final Map<Object, Object> clones
    ) {
        final Credential original = (Credential) t;
        return new Credential(
            new CredentialId(original.getId()),
            new EmailAddress(original.getEmail()),
            new Password(original.getPassword()),
            original.getRole()
        );
    }
}

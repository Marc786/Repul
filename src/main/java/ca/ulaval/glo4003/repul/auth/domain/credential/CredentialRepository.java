package ca.ulaval.glo4003.repul.auth.domain.credential;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;

public interface CredentialRepository {
    void save(Credential credential);

    Credential findUserCredential(EmailAddress email, Role role);
}

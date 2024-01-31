package ca.ulaval.glo4003.repul.auth.domain.credential;

import ca.ulaval.glo4003.lib.value_object.AccountId;
import java.util.Objects;

public class CredentialId {

    private final String id;

    public CredentialId(String id) {
        this.id = id;
    }

    public CredentialId(CredentialId credentialId) {
        this.id = credentialId.id;
    }

    public CredentialId(AccountId accountId) {
        this.id = accountId.toString();
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CredentialId that = (CredentialId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

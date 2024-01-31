package ca.ulaval.glo4003.repul.auth.domain.credential;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidCredentialsException;
import java.util.Objects;

public class Credential {

    private final CredentialId id;
    private final EmailAddress email;
    private final Password password;
    private final Role role;

    public Credential(CredentialId id, EmailAddress email, Password password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void verifyPassword(Password password) {
        if (!this.password.equals(password)) {
            throw new InvalidCredentialsException();
        }
    }

    public CredentialId getId() {
        return id;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Credential) obj;
        return (
            Objects.equals(this.id, that.id) &&
            Objects.equals(this.email, that.email) &&
            Objects.equals(this.password, that.password) &&
            Objects.equals(this.role, that.role)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role);
    }
}

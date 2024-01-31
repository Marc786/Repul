package ca.ulaval.glo4003.lib.value_object;

import java.util.Objects;

public final class EmailAddress {

    private final String email;

    public EmailAddress(String email) {
        this.email = email;
    }

    public EmailAddress(EmailAddress emailAddress) {
        this.email = emailAddress.email;
    }

    public String email() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        EmailAddress that = (EmailAddress) obj;
        return Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return email;
    }
}

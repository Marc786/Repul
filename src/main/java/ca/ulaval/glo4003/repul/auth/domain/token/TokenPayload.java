package ca.ulaval.glo4003.repul.auth.domain.token;

import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.auth.domain.exception.InvalidTokenException;
import java.util.Date;
import java.util.Objects;

public class TokenPayload {

    private final String id;
    private final Date expirationDate;
    private final Role role;

    public TokenPayload(String id, Date expirationDate, Role role) {
        this.id = id;
        this.expirationDate = expirationDate;
        this.role = role;
    }

    public void verifyExpiration() {
        Date currentDate = new Date();
        if (expirationDate.before(currentDate)) {
            throw new InvalidTokenException();
        }
    }

    public String getId() {
        return id;
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

        TokenPayload that = (TokenPayload) obj;
        return (
            Objects.equals(this.id, that.id) &&
            Objects.equals(this.expirationDate, that.expirationDate) &&
            Objects.equals(this.role, that.role)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expirationDate, role);
    }
}

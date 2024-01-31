package ca.ulaval.glo4003.repul.account.domain.value_object;

import java.util.Objects;

public class Name {

    private final String firstName;
    private final String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Name that = (Name) obj;
        return (
            Objects.equals(this.firstName, that.firstName) &&
            Objects.equals(this.lastName, that.lastName)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}

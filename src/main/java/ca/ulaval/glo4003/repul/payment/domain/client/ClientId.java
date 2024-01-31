package ca.ulaval.glo4003.repul.payment.domain.client;

public class ClientId {

    private final String value;

    public ClientId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClientId id = (ClientId) obj;
        return this.value.equals(id.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

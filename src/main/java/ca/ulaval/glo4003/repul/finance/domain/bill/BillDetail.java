package ca.ulaval.glo4003.repul.finance.domain.bill;

import java.util.Map;
import java.util.Objects;

public class BillDetail {

    private final Map<String, String> details;

    public BillDetail(Map<String, String> details) {
        this.details = details;
    }

    public Map<String, String> getAsMap() {
        return details;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BillDetail that)) {
            return false;
        }

        return Objects.equals(this.details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(details);
    }
}

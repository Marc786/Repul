package ca.ulaval.glo4003.repul.communication.domain;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import java.util.List;

public interface CarrierProfileClient {
    List<EmailAddress> getCarriersEmail();
}

package ca.ulaval.glo4003.repul.communication.infra;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.repul.account.api.carrier.CarrierProfileResource;
import ca.ulaval.glo4003.repul.communication.domain.CarrierProfileClient;
import java.util.List;

public class InternalCarrierProfileClient implements CarrierProfileClient {

    private final CarrierProfileResource carrierProfileResource;

    public InternalCarrierProfileClient(CarrierProfileResource carrierProfileResource) {
        this.carrierProfileResource = carrierProfileResource;
    }

    @Override
    public List<EmailAddress> getCarriersEmail() {
        return carrierProfileResource.getCarriersEmail();
    }
}

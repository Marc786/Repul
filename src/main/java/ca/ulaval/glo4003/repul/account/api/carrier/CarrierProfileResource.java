package ca.ulaval.glo4003.repul.account.api.carrier;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.account.api.carrier.dto.request.CreateCarrierProfileRequest;
import ca.ulaval.glo4003.repul.account.application.carrier.CarrierProfileService;
import ca.ulaval.glo4003.repul.account.domain.carrier.CarrierProfileId;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;

@Path("/carriers")
@Produces(MediaType.APPLICATION_JSON)
public class CarrierProfileResource {

    private final CarrierProfileService carrierProfileService;

    public CarrierProfileResource(CarrierProfileService carrierProfileService) {
        this.carrierProfileService = carrierProfileService;
    }

    @POST
    @RBAC(roles = { Role.ADMIN })
    public Response createCarrier(
        @NotNull @Valid CreateCarrierProfileRequest request,
        @Context UriInfo uriInfo
    ) {
        CarrierProfileId carrierProfileId = carrierProfileService.createCarrier(
            new Name(request.firstName(), request.lastName()),
            new EmailAddress(request.email()),
            new Password(request.password())
        );

        return Response
            .created(
                uriInfo.getAbsolutePathBuilder().path(carrierProfileId.toString()).build()
            )
            .build();
    }

    public List<EmailAddress> getCarriersEmail() {
        return carrierProfileService.getCarriersEmail();
    }
}

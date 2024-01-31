package ca.ulaval.glo4003.repul.account.api.cook;

import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.account.api.cook.dto.request.CreateCookProfileRequest;
import ca.ulaval.glo4003.repul.account.application.cook.CookProfileService;
import ca.ulaval.glo4003.repul.account.domain.cook.CookProfileId;
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

@Path("/cooks")
@Produces(MediaType.APPLICATION_JSON)
public class CookProfileResource {

    private final CookProfileService cookProfileService;

    public CookProfileResource(CookProfileService cookProfileService) {
        this.cookProfileService = cookProfileService;
    }

    @POST
    @RBAC(roles = { Role.ADMIN })
    public Response createCook(
        @NotNull @Valid CreateCookProfileRequest request,
        @Context UriInfo uriInfo
    ) {
        CookProfileId cookProfileId = cookProfileService.createCook(
            new Name(request.firstName(), request.lastName()),
            new EmailAddress(request.email()),
            new Password(request.password())
        );

        return Response
            .created(
                uriInfo.getAbsolutePathBuilder().path(cookProfileId.toString()).build()
            )
            .build();
    }
}

package ca.ulaval.glo4003.repul.account.api.customer;

import ca.ulaval.glo4003.lib.date.DateUtils;
import ca.ulaval.glo4003.lib.value_object.EmailAddress;
import ca.ulaval.glo4003.lib.value_object.Password;
import ca.ulaval.glo4003.middleware.auth.annotation.ACL;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.account.api.customer.assembler.CustomerProfileAssembler;
import ca.ulaval.glo4003.repul.account.api.customer.dto.request.CreateCustomerProfileRequest;
import ca.ulaval.glo4003.repul.account.api.customer.dto.response.CustomerProfileResponse;
import ca.ulaval.glo4003.repul.account.application.customer.CustomerProfileService;
import ca.ulaval.glo4003.repul.account.domain.customer.CustomerProfile;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.CustomerProfileId;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.Gender;
import ca.ulaval.glo4003.repul.account.domain.customer.value_object.StudentCard;
import ca.ulaval.glo4003.repul.account.domain.value_object.Name;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.time.LocalDate;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerProfileResource {

    private final CustomerProfileService customerProfileService;
    private final CustomerProfileAssembler customerAssembler;

    public CustomerProfileResource(
        CustomerProfileService customerProfileService,
        CustomerProfileAssembler customerAssembler
    ) {
        this.customerProfileService = customerProfileService;
        this.customerAssembler = customerAssembler;
    }

    @POST
    public Response createCustomer(
        @NotNull @Valid CreateCustomerProfileRequest createCustomerProfileRequest,
        @Context UriInfo uriInfo
    ) {
        LocalDate localDate = DateUtils.formatDateToLocalDate(
            createCustomerProfileRequest.birthDate()
        );
        Gender gender = Gender.fromString(createCustomerProfileRequest.gender());

        CustomerProfileId customerProfileId = customerProfileService.createCustomer(
            new EmailAddress(createCustomerProfileRequest.email()),
            new Password(createCustomerProfileRequest.password()),
            new Name(
                createCustomerProfileRequest.firstName(),
                createCustomerProfileRequest.lastName()
            ),
            localDate,
            gender,
            new CustomerProfileId(createCustomerProfileRequest.customerId()),
            new StudentCard(createCustomerProfileRequest.studentCardNumber())
        );

        return Response
            .created(
                uriInfo
                    .getAbsolutePathBuilder()
                    .path(customerProfileId.toString())
                    .build()
            )
            .build();
    }

    @GET
    @Path("/{customerId}")
    @ACL(accountId = "customerId")
    @RBAC(roles = { Role.CUSTOMER })
    public Response getCustomer(@NotNull @PathParam("customerId") String customerId) {
        CustomerProfileId customerProfileId = new CustomerProfileId(customerId);
        CustomerProfile customerProfile = customerProfileService.getCustomer(
            customerProfileId
        );

        CustomerProfileResponse customerProfileResponse =
            this.customerAssembler.toResponse(customerProfile);

        return Response.ok().entity(customerProfileResponse).build();
    }
}

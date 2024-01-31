package ca.ulaval.glo4003.repul.payment.api;

import ca.ulaval.glo4003.lib.value_object.Amount;
import ca.ulaval.glo4003.middleware.auth.annotation.ACL;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.payment.api.dto.request.AddCreditCardRequest;
import ca.ulaval.glo4003.repul.payment.api.dto.request.PaymentRequest;
import ca.ulaval.glo4003.repul.payment.application.PaymentService;
import ca.ulaval.glo4003.repul.payment.domain.client.ClientId;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardExpirationDate;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.CardNumber;
import ca.ulaval.glo4003.repul.payment.domain.client.credit_card.Ccv;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Path("/clients")
public class PaymentResource {

    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Response pay(@NotNull @Valid PaymentRequest paymentRequest) {
        ClientId clientId = new ClientId(paymentRequest.customerId());
        Amount amount = new Amount(paymentRequest.amount());

        paymentService.pay(clientId, amount);

        return Response.noContent().build();
    }

    @POST
    @Path("/{clientId}/credit-card")
    @ACL(accountId = "clientId")
    @RBAC(roles = { Role.CUSTOMER })
    public Response createClient(
        @NotBlank @PathParam("clientId") String clientIdParam,
        @NotNull @Valid AddCreditCardRequest request
    ) {
        ClientId clientId = new ClientId(clientIdParam);
        CardNumber creditCardNumber = new CardNumber(request.creditCardNumber());
        CardExpirationDate creditCardExpirationDate = new CardExpirationDate(
            request.creditCardExpirationDate()
        );
        Ccv creditCardCcv = new Ccv(request.creditCardCcv());

        paymentService.createClient(
            clientId,
            creditCardNumber,
            creditCardExpirationDate,
            creditCardCcv
        );

        return Response.noContent().build();
    }
}

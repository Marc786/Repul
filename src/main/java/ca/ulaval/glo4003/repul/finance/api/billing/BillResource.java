package ca.ulaval.glo4003.repul.finance.api.billing;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.date.DateUtils;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.finance.api.billing.dto.request.MealKitBillRequest;
import ca.ulaval.glo4003.repul.finance.application.bill.BillService;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.BuyerId;
import ca.ulaval.glo4003.repul.finance.domain.value_objects.ProductId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.time.LocalDate;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/bills")
public class BillResource {

    private final BillService billService;

    public BillResource(BillService billService) {
        this.billService = billService;
    }

    @POST
    @RBAC(roles = { Role.ADMIN })
    public void billMealKit(@NotNull @Valid MealKitBillRequest mealKitBillRequest) {
        BuyerId buyerId = new BuyerId(mealKitBillRequest.customerId());
        ProductId productId = new ProductId(mealKitBillRequest.productId());
        LocalDate deliveryDate = DateUtils.formatDateToLocalDate(
            mealKitBillRequest.deliveryDate()
        );
        MealKitType mealKitType = MealKitType.fromString(
            mealKitBillRequest.mealKitType()
        );

        billService.billMealKit(buyerId, productId, mealKitType, deliveryDate);
    }
}

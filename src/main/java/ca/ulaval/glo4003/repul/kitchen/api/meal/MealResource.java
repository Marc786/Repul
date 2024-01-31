package ca.ulaval.glo4003.repul.kitchen.api.meal;

import static ca.ulaval.glo4003.constant.Constants.ClockSetup;

import ca.ulaval.glo4003.lib.catalog.MealKitType;
import ca.ulaval.glo4003.lib.date.DateUtils;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.kitchen.api.meal.assembler.IngredientResponseAssembler;
import ca.ulaval.glo4003.repul.kitchen.api.meal.assembler.MealResponseAssembler;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.request.AddMealRequest;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.IngredientsResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealsResponse;
import ca.ulaval.glo4003.repul.kitchen.application.MealService;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meals;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;

@Produces(MediaType.APPLICATION_JSON)
@Path("/meals")
public class MealResource {

    private final MealService mealService;
    private final MealResponseAssembler mealResponseAssembler =
        new MealResponseAssembler();
    private final IngredientResponseAssembler ingredientResponseAssembler =
        new IngredientResponseAssembler();

    public MealResource(MealService mealService) {
        this.mealService = mealService;
    }

    public void addMealToPrepare(@NotNull @Valid AddMealRequest addMealRequest) {
        MealId mealId = new MealId(addMealRequest.mealId());
        MealKitType mealType = MealKitType.fromString(addMealRequest.mealKitType());
        LocalDate deliveryDate = LocalDate.parse(addMealRequest.deliveryDate());

        mealService.addMealToPrepare(mealId, mealType, deliveryDate);
    }

    @GET
    @RBAC(roles = { Role.COOK })
    public Response getMealsToPrepare(
        @NotBlank @QueryParam("endDate") String endDateParam
    ) {
        LocalDate currentDate = LocalDate.now(ClockSetup.getClock());
        LocalDate endDate = DateUtils.formatDateToLocalDate(endDateParam);

        Meals mealsToPrepare = mealService.getMealsToPrepare(currentDate, endDate);

        MealsResponse mealsResponse = mealResponseAssembler.toResponse(
            mealsToPrepare.getMeals()
        );

        return Response.ok(mealsResponse).build();
    }

    @GET
    @Path("/ingredients")
    @RBAC(roles = { Role.COOK })
    public Response getMealsToPrepareIngredients(
        @NotBlank @QueryParam("endDate") String endDateParam
    ) {
        LocalDate currentDate = LocalDate.now(ClockSetup.getClock());
        LocalDate endDate = DateUtils.formatDateToLocalDate(endDateParam);

        Meals mealsToPrepare = mealService.getMealsToPrepare(currentDate, endDate);

        IngredientsResponse ingredientsResponse = ingredientResponseAssembler.toResponse(
            mealsToPrepare.getIngredients()
        );

        return Response.ok(ingredientsResponse).build();
    }
}

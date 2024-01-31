package ca.ulaval.glo4003.repul.kitchen.api.cook;

import ca.ulaval.glo4003.middleware.auth.annotation.ACL;
import ca.ulaval.glo4003.middleware.auth.annotation.RBAC;
import ca.ulaval.glo4003.repul.auth.domain.credential.Role;
import ca.ulaval.glo4003.repul.kitchen.api.cook.assembler.MealIdAssembler;
import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.request.MealIdsRequest;
import ca.ulaval.glo4003.repul.kitchen.api.cook.dto.response.MealIdsResponse;
import ca.ulaval.glo4003.repul.kitchen.api.meal.assembler.MealResponseAssembler;
import ca.ulaval.glo4003.repul.kitchen.api.meal.dto.response.MealsResponse;
import ca.ulaval.glo4003.repul.kitchen.application.CookService;
import ca.ulaval.glo4003.repul.kitchen.domain.cook.CookId;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.Meal;
import ca.ulaval.glo4003.repul.kitchen.domain.meal.MealId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/cooks")
@Produces(MediaType.APPLICATION_JSON)
public class CookResource {

    private final CookService cookService;
    private final MealIdAssembler mealIdAssembler = new MealIdAssembler();
    private final MealResponseAssembler mealResponseAssembler =
        new MealResponseAssembler();

    public CookResource(CookService cookService) {
        this.cookService = cookService;
    }

    @POST
    @Path("/{cookId}/meals:assign")
    @ACL(accountId = "cookId")
    @RBAC(roles = { Role.COOK })
    public Response assignCookToMeal(
        @NotBlank @PathParam("cookId") String cookIdParam,
        @NotNull @Valid MealIdsRequest mealIdsRequest
    ) {
        CookId cookId = new CookId(cookIdParam);
        List<MealId> mealIds = mealIdAssembler.toMealIds(mealIdsRequest);

        List<MealId> assignedMealIds = cookService.assignCookToMeals(cookId, mealIds);
        MealIdsResponse mealIdsResponse = mealIdAssembler.toMealIdsResponse(
            assignedMealIds
        );

        return Response.ok().entity(mealIdsResponse).build();
    }

    @POST
    @Path("/{cookId}/meals:unassign")
    @ACL(accountId = "cookId")
    @RBAC(roles = { Role.COOK })
    public Response unassignCookFromMeal(
        @NotBlank @PathParam("cookId") String cookIdParam,
        @NotNull @Valid MealIdsRequest mealIdsRequest
    ) {
        CookId cookId = new CookId(cookIdParam);
        List<MealId> mealIds = mealIdAssembler.toMealIds(mealIdsRequest);

        List<MealId> unassignedMealIds = cookService.unassignCookFromMeal(
            cookId,
            mealIds
        );
        MealIdsResponse mealIdsResponse = mealIdAssembler.toMealIdsResponse(
            unassignedMealIds
        );

        return Response.ok().entity(mealIdsResponse).build();
    }

    @POST
    @Path("/{cookId}/meals:assemble")
    @ACL(accountId = "cookId")
    @RBAC(roles = { Role.COOK })
    public Response assembleMeal(
        @NotBlank @PathParam("cookId") String cookIdParam,
        @NotNull @Valid MealIdsRequest mealIdsRequest
    ) {
        CookId cookId = new CookId(cookIdParam);
        List<MealId> mealIds = mealIdAssembler.toMealIds(mealIdsRequest);

        List<MealId> assembledMealIds = cookService.assembleMeal(cookId, mealIds);
        MealIdsResponse mealIdsResponse = mealIdAssembler.toMealIdsResponse(
            assembledMealIds
        );

        return Response.ok().entity(mealIdsResponse).build();
    }

    @GET
    @Path("/{cookId}/meals")
    @ACL(accountId = "cookId")
    @RBAC(roles = { Role.COOK })
    public Response getAssignedMeals(@NotBlank @PathParam("cookId") String cookIdParam) {
        CookId cookId = new CookId(cookIdParam);

        List<Meal> meals = cookService.getAssignedMeals(cookId);
        MealsResponse mealsResponse = mealResponseAssembler.toResponse(meals);

        return Response.ok(mealsResponse).build();
    }
}

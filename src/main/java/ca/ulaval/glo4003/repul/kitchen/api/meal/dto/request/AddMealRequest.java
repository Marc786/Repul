package ca.ulaval.glo4003.repul.kitchen.api.meal.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AddMealRequest(
    @NotBlank String mealId,
    @NotBlank String mealKitType,
    @NotBlank String deliveryDate
) {}

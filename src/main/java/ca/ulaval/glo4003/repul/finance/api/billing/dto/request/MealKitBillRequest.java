package ca.ulaval.glo4003.repul.finance.api.billing.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MealKitBillRequest(
    @NotBlank String customerId,
    @NotBlank String productId,
    @NotBlank String deliveryDate,
    @NotBlank String mealKitType
) {}

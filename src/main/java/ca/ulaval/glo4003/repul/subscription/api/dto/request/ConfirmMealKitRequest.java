package ca.ulaval.glo4003.repul.subscription.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ConfirmMealKitRequest(@NotNull boolean acceptKit) {}

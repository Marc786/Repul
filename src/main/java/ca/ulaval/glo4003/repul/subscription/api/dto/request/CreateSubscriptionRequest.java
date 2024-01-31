package ca.ulaval.glo4003.repul.subscription.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.DayOfWeek;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateSubscriptionRequest(
    @NotBlank String frequency,
    @NotBlank String location,
    @NotBlank String mealKitType,
    @NotBlank String startDate,
    @NotNull DayOfWeek deliveringDayOfWeek
) {}

package ca.ulaval.glo4003.repul.kitchen.api.cook.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MealIdsRequest(@NotEmpty List<String> mealIds) {}

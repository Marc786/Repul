package ca.ulaval.glo4003.repul.payment.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentRequest(@NotBlank String customerId, @NotNull double amount) {}

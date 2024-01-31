package ca.ulaval.glo4003.repul.payment.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AddCreditCardRequest(
    @NotBlank String creditCardNumber,
    @NotBlank String creditCardExpirationDate,
    @NotBlank String creditCardCcv
) {}

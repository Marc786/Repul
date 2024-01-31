package ca.ulaval.glo4003.repul.account.api.carrier.dto.request;

import static ca.ulaval.glo4003.constant.Constants.Validator.EMAIL_REGEX;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateCarrierProfileRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @Email(regexp = EMAIL_REGEX) @NotBlank String email,
    @NotBlank String password
) {}
